"Global Alignment"

val input = scala.io.Source.fromFile("05.input").getLines()

val s1, s2 = input.next()

val sig = -5
val scores = scala.io.Source.fromFile("BLOSUM62.txt").getLines()

val ads = scores.next().trim.split("  ").map(_(0)).zipWithIndex.toMap

val table = scores.toList.map(_.split(" +").drop(1).map(_.toInt))

def lu(a: Char, b: Char) =
  table(ads(a))(ads(b))

val mat = new Array[Array[Int]](s1.length + 1)
(0 to s1.length).foreach(i => mat(i) = new Array[Int](s2.length + 1))

(0 to s1.length).foreach(i => mat(i)(0) = sig * i)
(0 to s2.length).foreach(i => mat(0)(i) = sig * i)

val back = new Array[Array[Int]](s1.length + 1)
(0 to s1.length).foreach(i => back(i) = new Array[Int](s2.length + 1))

for (i <- 1 to s1.length; j <- 1 to s2.length) mat(i)(j) = {
  val p = List(mat(i - 1)(j) + sig, mat(i)(j - 1) + sig, mat(i - 1)(j - 1) + lu(s1(i - 1), s2(j - 1))).zipWithIndex.sortBy(_._1).last
  back(i)(j) = p._2
  p._1
}

var (i, j) = (s1.length, s2.length)
var ss1 = ""
var ss2 = ""
while (i != 0 || j != 0) {
  back(i)(j) match {
    case 0 =>
      ss1 = s1(i - 1) + ss1
      ss2 = "-" + ss2
      i = i - 1
    case 1 =>
      ss1 = "-" + ss1
      ss2 = s2(j - 1) + ss2
      j = j - 1
    case 2 =>
      ss1 = s1(i - 1) + ss1
      ss2 = s2(j - 1) + ss2
      i = i - 1
      j = j - 1
  }
}

println(mat(s1.length)(s2.length))
println(ss1)
println(ss2)


