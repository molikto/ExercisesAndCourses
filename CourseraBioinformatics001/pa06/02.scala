"Manhattan Tourist"

val input = scala.io.Source.fromFile("02.input").getLines()

val n, m = input.next().toInt
val nm1 = (0 until n).map(a => input.next().split(" ").map(_.toInt).toIndexedSeq)
input.next()
val n1m = (0 until (n + 1)).map(a => input.next().split(" ").map(_.toInt).toIndexedSeq)

val s = new Array[Array[Int]](n + 1)
for (i <- 0 to n) s(i) = new Array[Int](m + 1)

for (i <- 1 to n) s(i)(0) = nm1(i - 1)(0) + s(i - 1)(0)
for (i <- 1 to m) s(0)(i) = n1m(0)(i - 1) + s(0)(i - 1)
for (i <- 1 to n; j <- 1 to m) s(i)(j) = math.max(s(i - 1)(j) + nm1(i - 1)(j), s(i)(j - 1) + n1m(i)(j - 1))

println(s(n)(m))




