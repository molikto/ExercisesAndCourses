"Eulerian Path Problem"


val input = scala.io.Source.fromFile("01.input").getLines()

val graph = input.toList.map(_.split(" -> ")).map(pa => (pa(0).toInt, pa(1).split(",").map(_.toInt).toList)).toMap.withDefaultValue(List.empty[Int])

val counts = new scala.collection.mutable.HashMap[Int, Int].withDefaultValue(0)

for (g <- graph;
  p <- g._2) {
  counts(g._1) += 1
  counts(p) -= 1
}

val unbs = counts.toList.filter(_._2 != 0).map(_._1).sorted

assert(unbs.size == 2)

val mg = graph.updated(unbs(0), graph(unbs(0)) ++ List(unbs(1)))


val rand = new scala.util.Random()

def eurPath(graph: Map[Int, List[Int]]) ={

}
def eurCycle(graph: Map[Int, List[Int]]) = {
  val size = graph.map(_._2.size).sum
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

val c = eurCycle(mg)

val i = c.sliding(2).zipWithIndex.find(_._1 == unbs).get._2

val path = c.slice(i + 1, c.length) ++ c.slice(1, i + 1)

println(path.mkString("->"))


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
