"Edit Distance"

val input = scala.io.Source.fromFile("01.input").getLines()

val s1, s2 = input.next()

val mat = new Array[Array[Int]](s1.length + 1)
(0 to s1.length).foreach(i => mat(i) = new Array[Int](s2.length + 1))

(0 to s1.length).foreach(i => mat(i)(0) = -i)
(0 to s2.length).foreach(i => mat(0)(i) = -i)

val back = new Array[Array[Int]](s1.length + 1)
(0 to s1.length).foreach(i => back(i) = new Array[Int](s2.length + 1))

for (i <- 1 to s1.length; j <- 1 to s2.length) mat(i)(j) = {
  val p = List(mat(i - 1)(j) -1, mat(i)(j - 1) -1, mat(i - 1)(j - 1) + (if (s1(i - 1) == s2(j - 1)) 0 else -1)).zipWithIndex.sortBy(_._1).last
  back(i)(j) = p._2
  p._1
}

println(-mat(s1.length)(s2.length))


