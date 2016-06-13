package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {
  


  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }


  property("insert, findMin and deleteMin") = forAll { a: List[Int] =>
    var fh = empty
    for (i <- a) {
      fh = insert(i, fh)
    }
    val e = for (i <- a.sorted) yield {
      val o = findMin(fh)
      fh = deleteMin(fh)
      i == o
    }
    e.forall(b => b)
  }


  property("isEmpty") = forAll{ a: List[Int] =>
    var fh = empty
    for (i <- a) {
      fh = insert(i, fh)
    }
    for (i <- a) {
      fh = deleteMin(fh)
    }
    isEmpty(fh)
  }

  property("merge") = forAll { (a: List[Int], b: List[Int]) =>
    val m = a ::: b
    var fh, fg = empty
    for (i <- a) {
      fh = insert(i, fh)
    }
    for (j <- b) {
      fg = insert(j, fg)
    }
    var ft = meld(fh, fg)

    val e = for (i <- m.sorted) yield {
      val o = findMin(ft)
      ft = deleteMin(ft)
      i == o
    }
    e.forall(b => b)
  }
}
