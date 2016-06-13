package kvstore

import akka.actor._
import kvstore.Arbiter._
import scala.concurrent.duration._
import scala.Some
import kvstore.Arbiter.Replicas
import scala.collection.mutable

object Replica {

  sealed trait Operation {
    def key: String
    def id: Long
  }
  case class Insert(key: String, value: String, id: Long) extends Operation
  case class Remove(key: String, id: Long) extends Operation
  case class Get(key: String, id: Long) extends Operation

  sealed trait OperationReply
  case class OperationAck(id: Long) extends OperationReply
  case class OperationFailed(id: Long) extends OperationReply
  case class GetResult(key: String, valueOption: Option[String], id: Long) extends OperationReply

  def props(arbiter: ActorRef, persistenceProps: Props): Props = Props(new Replica(arbiter, persistenceProps))
}

class Replica(val arbiter: ActorRef, persistenceProps: Props) extends Actor {
  import Replica._
  import Replicator._
  import Persistence._
  import context.dispatcher

  /*
   * The contents of this actor is just a suggestion, you can implement it in any way you like.
   */
  
  var kv = Map.empty[String, String]
  // a map from secondary replicas to replicators
  var secondaries = Map.empty[ActorRef, ActorRef]


  override def preStart() = {
    arbiter ! Arbiter.Join
  }

  def receive = {
    case JoinedPrimary   => context.become(leader)
    case JoinedSecondary => context.become(replica)
  }

  val leader: Receive = {
    case o: Operation => operator(o)
    case Replicas(l) =>
      val a = l - context.self
      val added = a -- secondaries.keySet
      val removed = secondaries.keySet -- a
      added.foreach { r =>
        val replicator = context.actorOf(Replicator.props(r))
        secondaries += Pair(r, replicator)
        kv.zipWithIndex.foreach { p =>
          replicator ! Replicate(p._1._1, Some(p._1._2), p._2)
        }
      }
      removed.map(secondaries).foreach { r =>
        r ! PoisonPill
      }
      wq.foreach { p =>
        p._2._3 --= removed.map(secondaries)
        checkFinish(p._1)
      }
    case p: Persisted => persist(p)
    case r: Replicated => persist(r)
  }

  val persistence = context.actorOf(persistenceProps)

  var wq = Map.empty[Long, (() => Unit, Cancellable, mutable.ListBuffer[ActorRef])]

  var maxSeq = 0L
  
  val replica: Receive = {
    case o: Get => operator(o)
    case s @ Snapshot(k, v, seq) =>
      if (seq <= maxSeq) {
        if (maxSeq == seq) {
          v match {
            case Some(a) => kv += Pair(k, a)
            case None => kv -= k
          }
          maxSeq += 1
        }
        val s = sender
        changeComplex(() => {
          s ! SnapshotAck(k, seq)
        }, k, v, seq)
      }
    case p: Persisted => persist(p)
  }


  val persist: Receive = {
    case Persisted(k, seq) =>
      if (wq.contains(seq)) {
        wq(seq)._2.cancel()
        checkFinish(seq)
      }
    case Replicated(k, seq) =>
      if (wq.contains(seq)) {
        wq(seq)._3 -= sender
        checkFinish(seq)
      }
  }

  def checkFinish(seq: Long) = {
    val p = wq(seq)
    if (p._2.isCancelled && p._3.isEmpty) {
      p._1()
      wq -= seq
    }
  }

  def changeComplex(aft: () => Unit, k: String, v: Option[String], id: Long) = {
    wq += Pair(id,
      (aft, context.system.scheduler.schedule(0 millis, 100 millis, persistence, Persist(k, v, id)), {
        val hs = new mutable.ListBuffer[ActorRef]
        hs ++= secondaries.values 
        hs
      }))
    for (a <- secondaries.values) a ! Replicate(k, v, id)
    val s = sender
    context.system.scheduler.scheduleOnce(1 second) {
      if (wq.contains(id)) {
        s ! OperationFailed(id)
        val p = wq(id)
        p._2.cancel()
        wq -= id
      }
    }
  }

  val operator: Receive = {
    case i @ Insert(key, value, id) =>
      kv = kv.updated(key, value)
      val s = sender
      changeComplex(() => {
        s ! OperationAck(id)
      }, key, Some(value), id)
    case r @ Remove(key, id) =>
      kv = kv - key
      val s = sender
      changeComplex(() => {
        s ! OperationAck(id)
      }, key, None, id)
    case g @ Get(key, id) =>
      sender ! GetResult(key, kv.get(key), id)
  }
}
