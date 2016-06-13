"Longest Repeat Problem"

val input = scala.io.Source.fromFile("03.input").getLines()

val s = input.next().toList
def backTree(ss: Seq[List[Char]]) = {
  (0 to ss.map(_.length).max).foldLeft(List((List.empty[Char], ss)))((l, _) => l.flatMap { l =>
    if (l._2.find(_.nonEmpty).isEmpty)
      List(l)
    else
      l._2.filter(_.nonEmpty).groupBy(_.head).map{ group =>
        (group._1 :: l._1, group._2.map(_.tail))
      }
  })
}


// this version... just can solve it... but still slow
// shit... the problem requres suffixTree.. but... just use it next problem... this is fine
def findr(i: Int) = backTree(s.sliding(i).toList).find(_._2.size > 1).map(_._1.reverse.mkString)

var l = 0
var r = s.length
var ls = ""
while (l < r - 1) {
  val o = findr((l + r) / 2)
  if (o.isDefined) {
    ls = o.get
    l = (l + r) / 2
  } else {
    r = (l + r) / 2
  }
  println(l + " " + r)
}

println(ls)

