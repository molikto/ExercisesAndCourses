"Longest Common Subsequence Problem"

val input = scala.io.Source.fromFile("03.input").getLines()

val s1, s2 = input.next()


val mat = new Array[Array[Int]](s1.length + 1)
(0 to s1.length).foreach(i => mat(i) = new Array[Int](s2.length + 1))

val back = new Array[Array[Int]](s1.length + 1)
(0 to s1.length).foreach(i => back(i) = new Array[Int](s2.length + 1))

for (i <- 1 to s1.length; j <- 1 to s2.length) mat(i)(j) = {
  val p = List(mat(i - 1)(j), mat(i)(j - 1), mat(i - 1)(j - 1) + (if (s1(i - 1) == s2(j - 1)) 1 else 0)).zipWithIndex.sortBy(_._1).last
  back(i)(j) = p._2
  p._1
}

var (i, j) = (s1.length, s2.length)
var ss = ""
while (i != 0 && j != 0) {
  if (back(i)(j) == 2 && s1(i - 1) == s2(j - 1)) ss = s1(i-1) + ss
  back(i)(j) match {
    case 0 => i = i - 1
    case 1 => j = j - 1
    case 2 => i = i - 1; j = j - 1
  }
}

println(ss)

