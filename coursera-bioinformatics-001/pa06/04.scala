"Longest Path in a DAG Problem"

val input = scala.io.Source.fromFile("04.input").getLines()

val b, s = input.next().toInt

val edges = input.toList.map(s => {
  val a = s.split(":")
  val fe = a(0).split("->")
  (fe(0).toInt, fe(1).toInt, a(1).toInt)
})



def ordering(edges   : Seq[(Int, Int, Int)]) = {
  val im = edges.flatMap(p => List(p._1, p._2)).max
  val marks = new Array[Int](im + 1) // 0 unmarked, 1 temp, 2 ow
  var order = List.empty[Int]
  def visit(n: Int):Unit = {
    if (marks(n) == 1)
      throw new IllegalArgumentException("Not a DAG")
    else if (marks(n) == 0) {
      marks(n) = 1
      edges.filter(_._1 == n).foreach(e => visit(e._2))
      marks(n) = 2
      order = order.::(n)
    }
  }
  while (marks.contains(0)) {
    visit(marks.zipWithIndex.find(_._1 == 0).get._2)
  }
  order.toIndexedSeq
}


val o = {
  val oo = ordering(edges)
  oo.slice(oo.indexOf(b), oo.indexOf(s) + 1)
}

val dis = new Array[Int](o.length)
val froms = new Array[Int](o.length)
dis(0) = 0
froms(0) = -1
for (i <- 1 until o.length) {
  val p = dis.zipWithIndex.take(i).map(par => {
    val pdis = par._1
    val index = par._2
    (pdis + edges.find(e => e._1 == o(index) && e._2 == o(i)).getOrElse((0, 0,-10000))._3, index)
  }).sortBy(_._1).last
  dis(i) = p._1
  froms(i) = p._2
}

println(o)
var track = List(o.length - 1)
var i = o.length - 1
while (i != 0) {
  track = track.::(froms(i))
  i = froms(i)
}
println(dis.last)
println(track)
println(track.map(i => o(i)).mkString("->"))

