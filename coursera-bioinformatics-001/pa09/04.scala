"Suffix Tree Construction Problem"

val input = scala.io.Source.fromFile("04.input").getLines()

val s = input.next()


var pics = List.empty[String]

def backTree(ss: Seq[String]) = {
  (0 to ss.map(_.length).max).foldLeft(List((List.empty[String], ss)))((l, _) => l.flatMap { l =>
    l._2.filter(_.nonEmpty).groupBy(_.head).map{ group =>
      val c = group._2.head
      val i = (0 until c.length).takeWhile(i => group._2.forall(_(i) == c(i))).last + 1
      pics = c.take(i) :: pics
      (c.take(i) :: l._1, group._2.map(_.drop(i)))
    }
  })
}

backTree((1 to s.length).map(l => s.slice(s.length - l, s.length)).toList)
println(pics.mkString("\n"))




