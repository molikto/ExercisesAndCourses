"Overlap Alignment Problem"

val input = scala.io.Source.fromFile("03.input").getLines()

val s1, s2 = input.next()

val mat = new Array[Array[Int]](s1.length + 1)
(0 to s1.length).foreach(i => mat(i) = new Array[Int](s2.length + 1))

val back = new Array[Array[(Int, Int, Char, Char)]](s1.length + 1)
(0 to s1.length).foreach(i => back(i) = new Array[(Int, Int, Char, Char)](s2.length + 1))

(0 to s2.length).foreach(j => mat(0)(j) = - 2000 * j)

for (i <- 1 to s1.length; j <- 1 to s2.length) mat(i)(j) = {
  val p = List(
    (
      mat(i - 1)(j) -2,
      (i - 1, j, s1(i - 1), '-')
      ),
    (
      mat(i)(j - 1) - 2,
      (i, j - 1, '-', s2(j - 1))
      ),
    (
      mat(i - 1)(j - 1) + (if (s1(i - 1) == s2(j - 1)) 1 else -2),
      (i - 1, j - 1, s1(i - 1), s2(j - 1))
      )
  ).sortBy(_._1).last
  back(i)(j) = p._2
  p._1
}


var max = 0
var maxI = (0, 0)
for (y <- 0 until mat(0).length) {
  val x = mat.length - 1
  val com = mat(x)(y)
  if (com >= max) {
    max = com
    maxI = (x, y)
  }
}

var (i, j) = maxI

//println(s1)
//println(s2)
//println(i, j)

var ss2 = ""//s2.substring(j, s2.length)
var ss1 = ""//ss2.map(_ => '-')
while (back(i)(j) != null) {
  val a = back(i)(j)
  i = a._1
  j = a._2
  ss1 = a._3 + ss1
  ss2 = a._4 + ss2
}


//println(i, j)

//ss1 = s1.substring(0, i) + ss1
//ss2 = s1.substring(0, i).map(_ => '-') + ss2

println(max)
val amax = ss1.zip(ss2).dropWhile(_._2 == '-').reverse.dropWhile(_._1 == '-').map { p =>
  if (p._1 == p._2) 1 else -2
}.sum

println(ss1)

println(ss2)

