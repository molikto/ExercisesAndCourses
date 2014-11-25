"Alignment with Affine Gap Penalties Problem"

val input = scala.io.Source.fromFile("04.input").getLines()

val s1, s2 = input.next()

val tab = {
  val ls = scala.io.Source.fromFile("BLOSUM62.txt").getLines()
  val ind = ls.next().trim.split(" +").map(_(0)).zipWithIndex.toMap
  val tab = ls.toList.map(_.drop(1).trim.split(" +").map(_.toInt))
  def lu(a: Char, b: Char) = tab(ind(a))(ind(b))
  lu _
}

val open = -11

val exten = -1

// 1: def the types
type Node = (Int, Int, Int)
type Graph = scala.collection.mutable.HashMap[Node, List[(Node, Int)]]

val graph = new Graph
val res = new scala.collection.mutable.HashMap[Node, (Int, Node)]
// 2: construct the graph

for (
  i <- 0 to s1.length;
  j <- 0 to s2.length
) {
 graph((i, j, 0)) = List(
    if (i > 0 && j > 0) Some(((i - 1, j - 1, 0), tab(s1(i - 1), s2(j - 1)))) else None, // this is ...
    Some(((i, j, 2), 0)),
    Some(((i, j, 1), 0))
 ).flatten
  graph((i, j, 1)) = List(
    if (i > 0) Some(((i - 1, j, 1), exten)) else None,
    if (i > 0) Some(((i - 1, j, 0), open)) else None
  ).flatten
  graph((i, j, 2)) = List(
    if (j > 0) Some(((i, j - 1, 2), exten)) else None,
    if (j > 0) Some(((i, j - 1, 0), open)) else None
  ).flatten
}

for (
  i <- 0 to s1.length;
  j <- 0 to s2.length;
  l <- List(2, 1, 0)
) {
  val k = (i, j, l)
  res(k) =
    if (k == (0, 0, 0)) (0, null) else {
      val a = graph(k).map { p =>
        (p._2 + res(p._1)._1, p._1)
      }
      if (a.isEmpty) (-10000000, null) else a.maxBy(_._1)
    }
}

var p = (s1.length, s2.length, 0)

println(res(p)._1)

var l = List.empty[Node]
while (p != null) {
  l = l.::(p)
  p = res(p)._2
}

val a = l.map(p => (p._1, p._2)).sliding(2).map(l => (l(0), l(1))).filter(p => p._1 != p._2).map { p =>
 if (p._1._1 == p._2._1 - 1 && p._1._2 == p._2._2 - 1)
   (s1(p._1._1), s2(p._1._2))
 else if (p._1._1 == p._2._1 && p._1._2 == p._2._2 - 1)
   ('-', s2(p._1._2))
  else if (p._1._1 == p._2._1 -1 && p._1._2 == p._2._2)
   (s1(p._1._1), '-')
  else {
   throw new IllegalStateException
   ('-','-')
 }
}.toList.unzip

println(a._1.mkString)
println(a._2.mkString)
/*

another not working version
def dp(d1: Int, d2: Int, d3: Int)(f: (Array[Array[Array[Int]]], Int, Int, Int) => (Int, (String, String))) = {
  val a = Array.ofDim[Int](d1, d2, d3)
  val b = Array.ofDim[(String, String)](d1, d2, d3)
  for (i1 <- 0 until d1; i2 <- 0 until d2; i3 <- 0 until d3) {
    val p = f(a, i1, i2, i3)
    a(i1)(i2)(i3) = p._1
    b(i1)(i2)(i3) = p._2
  }
  (a, b)
}

val MIN = -0xFFFFFF
val IP = ("WTF!!!", "WTF!!!")

val mat = dp(s1.length + 1, s2.length + 1, 3) { (arr, x, y, i) =>
  ?
}

*/
/*


A stackoverflow version...

I am wondering if I can write beautiful dynamic code in scala

and I found that the lazy thing is fancinating but it turn out a bad idea

because in dp, WE KNOW EXACTLY the dependency of the vals, so we can calcluate them

a workaround is to ecplictly call the lazy vals one to make the effect happen. but this is UGLY

so maybe some kind of memorization is good....


class Lazy[A](x: => A) {
  lazy val value = x
}

object Lazy {
  def apply[A](x: => A) = new Lazy(x)
  implicit def fromLazy[A](z: Lazy[A]): A = z.value
  implicit def toLazy[A](x: => A): Lazy[A] = Lazy(x)
}

import Lazy._


// things I found fancy is that ... oh!!
lazy val arr: Array[Array[Array[Lazy[(Int, (String, String))]]]] =  Array.tabulate(s1.length + 1, s2.length + 1, 3) { (x, y, i) =>
    if (x == 0 && y == 0) (if (i == 0) 0 else -0xFFFFFF, null)
    else {
      val a: List[(Int, (String, String))] = i match {
        case 0 =>
          List(
            (
              if (x > 0 && y > 0)
                arr(x - 1)(y - 1)(0)._1 + tab(s1(x - 1), s2(y - 1))
              else
                -0xFFFFFF
              ,
              null
              ),
            (
              arr(x)(y)(1)._1
              ,
              ("", "")
              ),
            (
              arr(x)(y)(2)._1
              ,
              ("", "")
              )
          )
        case 1 =>
          List(
            (
              if (x > 0) arr(x - 1)(y)(1)._1 - k else -0xFFFFFF
              ,
              if (x > 0) (s1(x -1 ).toString, s2( y).toString) else ("", "")
              ),
            (
              if (x > 0) arr(x - 1)(y)(0)._1 - s else -0xFFFFFF
              ,
              if (x > 0) (s1(x -1 ).toString, s2( y).toString) else ("", "")
              )
          )
        case 2 =>
          List(
            (
              if (y > 0) arr(x)(y - 1)(2)._1 - k else -0xFFFFFF
              ,
              if (y > 0) (s1(x).toString, s2(y - 1).toString) else ("", "")
              ),
            (
              if (y > 0) arr(x)(y - 1)(0)._1 - s else -0xFFFFFF
              ,
              if (y > 0) (s1(x).toString, s2(y - 1).toString) else ("", "")
              )
          )
        case _ => List.empty[(Int, (String, String))]
      }
      a.sortBy(_._1).last
    }
}


println(arr(s1.length)(s2.length)(0)._1)


 */
