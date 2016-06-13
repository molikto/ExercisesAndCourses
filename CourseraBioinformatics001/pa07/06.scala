"Linear Space Alignment"

val input = scala.io.Source.fromFile("06.input").getLines()

val s1, s2 = input.next()

val tab = {
  val ls = scala.io.Source.fromFile("BLOSUM62.txt").getLines()
  val ind = ls.next().trim.split(" +").map(_(0)).zipWithIndex.toMap
  val tab = ls.toList.map(_.drop(1).trim.split(" +").map(_.toInt))
  def lu(a: Char, b: Char) = tab(ind(a))(ind(b))
  lu _
}

val PA = -5


def maxScores(s1: String, s2: String) = {
  var scores = (0 to s1.length).map(PA *)
  (0 until s2.length) foreach { j =>
    scores = (0 until s1.length).foldLeft(List(scores(0) + PA)) { (list, i) =>
      list.::(List(list.head + PA, scores(i) + tab(s1(i), s2(j)), scores(i + 1) + PA).max)
    }.toIndexedSeq.reverse
  }
  scores
}

def middleNode(s1: String, s2: String) = {
  val m = s2.size / 2
  val sl = maxScores(s1, s2.substring(0, m))
  val sr = maxScores(s1.reverse, s2.substring(m, s2.length).reverse).reverse
  ((0 to s1.length).map(i => (i, sl(i) + sr(i))).maxBy(_._2)._1, m)
}

def lsa(s1: String, s2: String): (String, String) = {
  if (s1.length == 0) (s2.map(_ => '-'), s2)
  else if (s2.length == 0) (s1, s1.map(_ => '-'))
  else if (s1.length == 1 && s2.length == 1) {
    if (tab(s1(0), s2(0)) > PA * 2)
      (s1, s2)
    else
      (s1 + "-", "-" + s2)
  } else {
    val m = {
      if (s1.length > s2.length) {
        val m0 = middleNode(s2, s1)
        (m0._2, m0._1)
      } else {
        middleNode(s1, s2)
      }
    }
    val a = lsa(s1.substring(0, m._1), s2.substring(0, m._2))
    val b = lsa(s1.substring(m._1, s1.length), s2.substring(m._2, s2.length))
    (a._1 + b._1, a._2 + b._2)
  }
}

val res = lsa(s1, s2)

val score = res._1.zip(res._2).map { p =>
  if (p._1 == '-' || p._2 == '-') PA else tab(p._1, p._2)
}.sum

println(score)
println(res._1)
println(res._2)
