"Change Problem"

val input = scala.io.Source.fromFile("01.input").getLines()

val m = input.next().toInt
val BIG = m * 100
val cs = input.next().split(",").map(_.toInt)

val ops = new Array[Int](m + 1)
for (i <- 1 to m) {
  ops(i) = ((for (c <- cs; if i >= c; b <- 0 to ((i-c)/2)) yield ops(b) + ops(i - c - b) + 1) ++ List(BIG)).min
}

println(ops(m))

