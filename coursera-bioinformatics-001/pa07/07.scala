"Multiple Longest Common Subsequence Problem"

val input = scala.io.Source.fromFile("07.input").getLines()

val s1, s2, s3 = input.next()

val scores = Array.ofDim[(Int, (Int, Int, Int))](s1.length + 1, s2.length + 1, s3.length + 1)


for (i1 <- 0 to s1.length)
  for (i2 <- 0 to s2.length)
    for (i3 <- 0 to s3.length)
      scores(i1)(i2)(i3) = {
        def mkPair(i1: Int, i2: Int, i3: Int, diff: Int = 0) = {
          (scores(i1)(i2)(i3)._1 + diff, (i1, i2, i3))
        }
        if (i1 + i2 + i3 == 0) (0, null)
        else if (i1 * i2 * i3 == 0) (0, (0 max (i1 - 1), 0 max (i2 - 1), 0 max (i3 - 1)))
        else List(
          mkPair(i1 - 1, i2 - 1, i3 - 1, if (s1(i1 - 1) == s2(i2 - 1) && s1(i1 - 1) == s3(i3 - 1)) 1 else 0),
          mkPair(i1, i2 - 1, i3 - 1),
          mkPair(i1 - 1, i2, i3 - 1),
          mkPair(i1 - 1, i2 - 1, i3),
          mkPair(i1 - 1, i2, i3),
          mkPair(i1, i2 - 1, i3),
          mkPair(i1, i2, i3 - 1)
        ).maxBy(_._1)
      }

var p = (s1.length, s2.length, s3.length)

println(scores.last.last.last._1)

var l = List.empty[(Int, Int, Int)]
while (p != null) {
  l = l.::(p)
  p = scores(p._1)(p._2)(p._3)._2
}

val res = l.sliding(2).map(p => (p(0), p(1))).map { p =>
  val p1 = p._1
  val p2 = p._2
  (
    if (p1._1 == p2._1) '-' else s1(p1._1),
    if (p1._2 == p2._2) '-' else s2(p1._2),
    if (p1._3 == p2._3) '-' else s3(p1._3)
    )
}.toList

println(res.map(_._1).mkString)
println(res.map(_._2).mkString)
println(res.map(_._3).mkString)
