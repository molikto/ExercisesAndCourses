"Fitting Alignment Problem"

val input = scala.io.Source.fromFile("02.input").getLines()

val s1, s2 = input.next()

val mat = new Array[Array[Int]](s1.length + 1)
(0 to s1.length).foreach(i => mat(i) = new Array[Int](s2.length + 1))

val back = new Array[Array[(Int, Int, Char, Char)]](s1.length + 1)
(0 to s1.length).foreach(i => back(i) = new Array[(Int, Int, Char, Char)](s2.length + 1))

(0 to s2.length).foreach(j => mat(0)(j) = -j)

for (i <- 1 to s1.length; j <- 1 to s2.length) mat(i)(j) = {
  val p = List(
    (
      mat(i - 1)(j) -1,
      (i - 1, j, s1(i - 1), '-')
      ),
    (
      mat(i)(j - 1) -1,
      (i, j - 1, '-', s2(j - 1))
      ),
    (
      mat(i - 1)(j - 1) + (if (s1(i - 1) == s2(j - 1)) 1 else -1),
      (i -1, j - 1, s1(i - 1), s2(j - 1))
      ),
    (
      if (j == 1) 0 else -100000,
      (0, 0, '#', '#')
      )
  ).sortBy(_._1).last
  back(i)(j) = p._2
  p._1
}


var max = 0
var maxI = (0, 0)
for (x <- 0 until mat.length) {
  val y = mat(x).length - 1
  val com = mat(x)(y)
  if (com > max) {
    max = com
    maxI = (x, y)
  }
}

var (i, j) = maxI
var ss1 = ""
var ss2 = ""
while ((i != 0 || j != 0) && back(i)(j) != null) {
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

