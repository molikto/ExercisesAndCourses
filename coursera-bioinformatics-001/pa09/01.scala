"Trie Construction Problem"

val input = scala.io.Source.fromFile("01.input").getLines()

val ss = input.toList.map(_.toList)

var n = 1
var branchs = List.empty[(Int, Int, Char)]
(0 to ss.map(_.length).max).foldLeft(List((List((' ', 1)), ss)))((l, _) => l.flatMap { l =>
  val rems = l._2
  val up = l._1
  rems.filter(_.nonEmpty).groupBy(_.head).map{ group =>
    n += 1
    branchs = (up.head._2, n, group._1) :: branchs
    ((group._1,n) :: up, group._2.map(_.tail))
  }
})

println(branchs.map(p => p._1 + " " + p._2 + " " + p._3).mkString("\n"))


