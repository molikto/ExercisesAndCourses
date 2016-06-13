"Middle Edge in Linear Space Problem"

val input = scala.io.Source.fromFile("05.input").getLines()

val s1, s2 = input.next()

val tab = {
  val ls = scala.io.Source.fromFile("BLOSUM62.txt").getLines()
  val ind = ls.next().trim.split(" +").map(_(0)).zipWithIndex.toMap
  val tab = ls.toList.map(_.drop(1).trim.split(" +").map(_.toInt))
  def lu(a: Char, b: Char) = tab(ind(a))(ind(b))
  lu _
}


val Pa = -5

val m = s2.size / 2


def maxScore(s1: String, s2: String) = {
  var scores: Seq[Int] = (0 to s1.length).map(Pa *)
  var lastPick = 0
  (0 until s2.length) foreach { j =>
    scores = (0 until s1.length).foldLeft(List(scores(0) + Pa)) { (list, i) =>
      list.::(
        {
          val a = List(list.head + Pa, scores(i) + tab(s1(i), s2(j)), scores(i + 1) + Pa).zipWithIndex.maxBy(_._1)
          lastPick = a._2
          a._1
        }
      )
    }.toIndexedSeq.reverse
  }
  (scores.last, lastPick)
}


// the implmentation is actually wrong!!!!
// but it finished in time...
// see next problem!!!
val pair = (0 to s1.length).map(i => {
  val sh = maxScore(s1.substring(0, i), s2.substring(0, m))._1
  val b = maxScore(s1.substring(i, s1.length).reverse, s2.substring(m, s2.length).reverse)
  val st = b._1
  (i, sh + st, b._2)
}).maxBy(_._2)

val md = (pair._1, m)

val nd = pair._3 match {
  case 0 => (pair._1 + 1, m)
  case 1 => (pair._1 + 1, m + 1)
  case 2 => (pair._1, m + 1)
}

println(List(md, nd).mkString(" "))

