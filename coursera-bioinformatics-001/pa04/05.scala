"Eulerian Cycle Problem"


val input = scala.io.Source.fromFile("05.input").getLines()

val graph = input.toList.map(_.split(" -> ")).map(pa => (pa(0).toInt, pa(1).split(",").map(_.toInt).toList)).toMap


val size = graph.map(_._2.size).sum

val rand = new scala.util.Random()
def eurCycle(graph: Map[Int, List[Int]]) = {
  val e = refineWhile(List(0, graph(0)(0))) { cycle =>
    val cdjs = cycle.sliding(2).map(l => (l(0), l(1))).toList
    val c = graph(cycle.last).find(adj => !cdjs.contains((cycle.last, adj))) match {
      case Some(a) => cycle ++ List(a)
      case None =>
        assert(cycle.head == cycle.last, println(cycle))
        val a = cycle.find(a => graph(a).exists(o => !cdjs.contains((a, o)))).get
        val i = cycle.indexOf(a)
        cycle.slice(i, cycle.length) ++ cycle.slice(1, i + 1)
    }
    (c, cycle.length < size)
  }
  e ++ List(e(0))
}




println(eurCycle(graph).mkString("->"))

def refineWhile[T](init: T)(body: T => (T, Boolean)) = {
  var now = init
  var conti = true
  while(conti) {
    val r = body(now)
    conti = r._2
    if (conti) now = r._1
  }
  now
}
def refineWhile2[T](init: T)(body: (T, T) => (T, Boolean, Boolean)) = {
  var now = init
  var last = init
  var conti = true
  while(conti) {
    val r = body(now, last)
    last = r._1
    val rep = r._2
    if (rep) now = last
    conti = r._3
  }
  now
}
