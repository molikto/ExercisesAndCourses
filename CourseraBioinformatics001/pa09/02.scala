"Multiple Pattern Matching Problem"

val input = scala.io.Source.fromFile("02.input").getLines()

val tex = input.next()
val ss = input.toList

import scala.collection.mutable
val pos = new Array[mutable.Set[Int]](ss.length)
for (i <- 0 until ss.length) pos(i) = mutable.Set.empty[Int]
val ssMap = ss.zipWithIndex.toMap

def tree(s: Seq[String]) = {
  var n = 0
  var branchs = List.empty[(Int, (Char, Set[Int]))]
  (0 to ss.map(_.length).max).foldLeft(List((List((' ', 0)), ss)))((l, _) => l.flatMap { l =>
    val rems = l._2
    val up = l._1
    val cc = rems.filter(_.nonEmpty).groupBy(_.head).map{ group =>
      n += 1
      ((group._1,n) :: up, group._2.map(_.tail))
    }
    branchs = (up.head._2, (up.head._1, cc.map(_._1.head._2).toSet)) :: branchs
    cc
  })
  branchs.toMap
}


val t = tree(ss)


for (i <- 0 until tex.length) {
  var pt = 0
  var p = 0
  while (p != -1 && pt + i < tex.length) {
    if (t(p)._2.isEmpty)
      pos(ssMap(tex.substring(i, i + pt))) += i
    p = t(p)._2.find(z => t(z)._1 == tex(i + pt)) match {
      case Some(a) => a
      case None => -1
    }
    pt += 1
  }
}

println(pos.map(_.toList.sorted.mkString(" ")).mkString("\n"))
