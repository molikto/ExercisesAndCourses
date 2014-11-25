"2-Break Distance Problem"

val input = scala.io.Source.fromFile("03.input").getLines()

val s1, s2 = input.next().drop(1).dropRight(1).split("\\)\\(").map(_.split(" ").map(_.dropWhile(_ == '+').toInt).toIndexedSeq)

//def countBp(ns: Seq[Int]) = (List(0) ++ ns ++ List(ns.length + 1)).sliding(2).count(p => p(0) - p(1) != -1)
//def c(s: Seq[Seq[Int]]) = s.map(a => countBp(a)).sum

// +1 means a end, -1 means a start
def graphy(s: Seq[Int]) = (s ++ List(s.head)).sliding(2).map(a => (a(0), a(1))).flatMap { p =>
  val a = p._1
  val b = p._2
  List((a, -b), (-b, a))
}.toMap

val blocks = {
  s1.map(_.size).sum
}

def fraphy(s: Seq[Seq[Int]]) = s.map(graphy).foldLeft(Map.empty[Int, Int]) { (m, a) => m ++ a}

import scala.collection.mutable

def bpraph(s1: Seq[Seq[Int]], s2: Seq[Seq[Int]]) = {
  val f = List(mutable.Map.empty ++ fraphy(s1), mutable.Map.empty ++ fraphy(s2))
  Stream.from(0).flatMap(_ => List(0, 1)).takeWhile(_ => f(0).nonEmpty && f(1).nonEmpty)
    .foldLeft(List(List(f(0).head._1))) { (l, i) =>
    val a = l.head.head
    if (f(i).contains(a)) {
      val b = f(i)(a)
      f(i).remove(a)
      f(i).remove(b)
      l.tail.::(l.head.::(b))
    } else {
      l.::(List(f(i).head._1))
    }
  }
}


println(blocks - bpraph(s1, s2).size)





