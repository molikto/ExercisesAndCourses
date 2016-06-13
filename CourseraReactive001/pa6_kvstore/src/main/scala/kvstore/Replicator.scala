package kvstore

import akka.actor.{Cancellable, Props, Actor, ActorRef}
import scala.concurrent.duration._

object Replicator {
  case class Replicate(key: String, valueOption: Option[String], id: Long)
  case class Replicated(key: String, id: Long)
  
  case class Snapshot(key: String, valueOption: Option[String], seq: Long)
  case class SnapshotAck(key: String, seq: Long)

  def props(replica: ActorRef): Props = Props(new Replicator(replica))
}

class Replicator(val replica: ActorRef) extends Actor {
  import Replicator._
  import Replica._
  import context.dispatcher
  
  /*
   * The contents of this actor is just a suggestion, you can implement it in any way you like.
   */


  // map from sequence number to pair of sender and request
  var acks = Map.empty[Long, (() => Unit, Cancellable)]

  var _seqCounter = 0L
  def nextSeq = {
    val ret = _seqCounter
    _seqCounter += 1
    ret
  }

  def receive: Receive = {
    case r @ Replicate(k, v, id) => {
      val seq = nextSeq
      val c = context.system.scheduler.schedule(0 millis, 200 millis, replica, Snapshot(k, v, seq))
      val s = sender
      acks += Pair(seq, Pair(() => {
        s ! Replicated(k, id)
      }, c))
    }
    case p @ SnapshotAck(key, seq) => {
      acks(seq)._2.cancel()
      acks(seq)._1()
      acks -= seq
    }
  }
}
