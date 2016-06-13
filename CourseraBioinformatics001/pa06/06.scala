"Local Alignment"

val input = scala.io.Source.fromFile("06.input").getLines()

val s1, s2 = input.next()

val sig = -5

var lu = {
  val scores = scala.io.Source.fromFile("PAM250_1.txt").getLines()
  val ads = scores.next().trim.split("  ").map(_(0)).zipWithIndex.toMap
  val table = scores.toList.map(_.split(" +").drop(1).map(_.toInt))
  def lu(a: Char, b: Char) =
    table(ads(a))(ads(b))
  lu _
}


val mat = new Array[Array[Int]](s1.length + 1)
(0 to s1.length).foreach(i => mat(i) = new Array[Int](s2.length + 1))

val back = new Array[Array[(Int, Int, Char, Char)]](s1.length + 1)
(0 to s1.length).foreach(i => back(i) = new Array[(Int, Int, Char, Char)](s2.length + 1))

for (i <- 1 to s1.length; j <- 1 to s2.length) mat(i)(j) = {
  val p = List(
    (
      mat(i - 1)(j) + sig,
      (i - 1, j, s1(i - 1), '-')
    ),
    (
      mat(i)(j - 1) + sig,
      (i, j - 1, '-', s2(j - 1))
    ),
    (
      mat(i - 1)(j - 1) + lu(s1(i - 1), s2(j - 1)),
      (i -1, j - 1, s1(i - 1), s2(j - 1))
    ),
    (
      0,
      (0, 0, '#', '#')
    )
  ).sortBy(_._1).last
  back(i)(j) = p._2
  p._1
}


var max = 0
var maxI = (0, 0)
for (x <- 0 until mat.length)
  for (y <- 0 until mat(x).length) {
    val com = mat(x)(y)
    if (com > max) {
      max = com
      maxI = (x, y)
    }
  }

var (i, j) = maxI
var ss1 = ""
var ss2 = ""
while (i != 0 || j != 0) {
  val a = back(i)(j)
  i = a._1
  j = a._2
  ss1 = a._3 + ss1
  ss2 = a._4 + ss2
}

if (ss1(0) == '#') ss1 = ss1.drop(1)
if (ss2(0) == '#') ss2 = ss2.drop(1)

println(max)

println(ss1)
println(ss2)

