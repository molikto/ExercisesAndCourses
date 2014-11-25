/**
 * Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
 */
package actorbintree

import akka.actor._
import scala.collection.immutable.Queue

object BinaryTreeSet {

  trait Operation {
    def requester: ActorRef
    def id: Int
    def elem: Int
  }

  trait OperationReply {
    def id: Int
  }

  /** Request with identifier `id` to insert an element `elem` into the tree.
    * The actor at reference `requester` should be notified when this operation
    * is completed.
    */
  case class Insert(requester: ActorRef, id: Int, elem: Int) extends Operation

  /** Request with identifier `id` to check whether an element `elem` is present
    * in the tree. The actor at reference `requester` should be notified when
    * this operation is completed.
    */
  case class Contains(requester: ActorRef, id: Int, elem: Int) extends Operation

  /** Request with identifier `id` to remove the element `elem` from the tree.
    * The actor at reference `requester` should be notified when this operation
    * is completed.
    */
  case class Remove(requester: ActorRef, id: Int, elem: Int) extends Operation

  /** Request to perform garbage collection*/
  case object GC

  /** Holds the answer to the Contains request with identifier `id`.
    * `result` is true if and only if the element is present in the tree.
    */
  case class ContainsResult(id: Int, result: Boolean) extends OperationReply
  
  /** Message to signal successful completion of an insert or remove operation. */
  case class OperationFinished(id: Int) extends OperationReply

}


class BinaryTreeSet extends Actor {
  import BinaryTreeSet._
  import BinaryTreeNode._

  def createRoot: ActorRef = context.actorOf(BinaryTreeNode.props(0, initiallyRemoved = true))

  var root = createRoot

  // optional
  var pendingQueue = Queue.empty[Operation]

  // optional
  def receive = normal

  // optional
  /** Accepts `Operation` and `GC` messages. */
  val normal: Receive = {
    case GC =>
      val nr = createRoot
      // if a root element recieved a copyTo, he will not recieve any op until he messaged out copyFinished
      root ! CopyTo(nr)
      context.become(garbageCollecting(nr), true)
    case i: Operation => root ! i
  }

  // optional
  /** Handles messages while garbage collection is performed.
    * `newRoot` is the root of the new binary tree where we want to copy
    * all non-removed elements into.
    */
  def garbageCollecting(newRoot: ActorRef): Receive = {
    case CopyFinished => {
      root = newRoot
      // this should be in a single threaded manner!!
      // so this code is ok
      for (p <- pendingQueue) normal(p)
      pendingQueue = Queue.empty
      context.become(normal, true)
    }
    case GC => Unit
    case x: Operation => {
      pendingQueue = pendingQueue.enqueue(x)
    }
  }
}

object BinaryTreeNode {
  trait Position

  case object Left extends Position
  case object Right extends Position

  case class CopyTo(treeNode: ActorRef)
  case object CopyFinished

  def props(elem: Int, initiallyRemoved: Boolean) = Props(classOf[BinaryTreeNode],  elem, initiallyRemoved)
}


class BinaryTreeNode(val elem: Int, initiallyRemoved: Boolean) extends Actor {
  import BinaryTreeNode._
  import BinaryTreeSet._

  var subtrees = Map[Position, ActorRef]()
  var removed = initiallyRemoved

  // optional
  def receive = normal

  // optional
  /** Handles `Operation` messages and `CopyTo` requests. */
  val normal: Receive = {
    case i @ Contains(r, id, e) =>
      if (elem == e) {
          r ! ContainsResult(id, !removed)
      }
      else if (e < elem) {
        if (subtrees.contains(Left)) subtrees(Left) ! i
        else r ! ContainsResult(id, false)
      } else if (subtrees.contains(Right)) subtrees(Right) ! i
      else r ! ContainsResult(id, false)
    case i @ Insert(r, id, e) =>
      if (elem == e) {
        removed = false
        r ! OperationFinished(id)
      }
      else if (e < elem) {
        if (subtrees.contains(Left)) subtrees(Left) ! i
        else {
          subtrees = subtrees.updated(Left, context.actorOf(props(e, false)))
          r ! OperationFinished(id)
        }
      } else if (subtrees.contains(Right)) subtrees(Right) ! i
      else {
        subtrees = subtrees.updated(Right, context.actorOf(props(e, false)))
        r ! OperationFinished(id)
      }
    case i @ Remove(r, id, e) =>
      if (elem == e) {
        removed = true
        r ! OperationFinished(id)
      } else if (e < elem) {
        if (subtrees.contains(Left)) subtrees(Left) ! i
        else r ! OperationFinished(id)
      } else if (subtrees.contains(Right)) subtrees(Right) ! i
      else r ! OperationFinished(id)
    // when receiving a copyTo, o has no Left and Right!
    case i @ CopyTo(o) => {
      var ex = Set.empty[ActorRef]
      if (!removed)
        o ! Insert(self, -1, elem)
      if (subtrees.contains(Left))
        ex = ex + subtrees(Left)
      if (subtrees.contains(Right))
        ex = ex + subtrees(Right)
      checkBecome(ex, removed)
      if (subtrees.contains(Left))
        subtrees(Left) ! CopyTo(o)
      if (subtrees.contains(Right))
        subtrees(Right) ! CopyTo(o)
    }
  }

  def checkBecome(expected: Set[ActorRef], insertConfirmed: Boolean) = {
    if (expected.isEmpty && insertConfirmed) {
      context.parent ! CopyFinished
      context.stop(self)
    } else {
      context.become(copying(expected, insertConfirmed), true)
    }
  }
  // optional
  /** `expected` is the set of ActorRefs whose replies we are waiting for,
    * `insertConfirmed` tracks whether the copy of this node to the new tree has been confirmed.
    */
  def copying(expected: Set[ActorRef], insertConfirmed: Boolean): Receive = {
    // WATCH THE CASE CLASS!!!!
    case _: OperationFinished => {
      checkBecome(expected, true)
    }
    case CopyFinished => {
      checkBecome(expected - sender, insertConfirmed)
    }
  }

}
