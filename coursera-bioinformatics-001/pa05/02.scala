"String Reconstruction Problem"


val input = scala.io.Source.fromFile("02.input").getLines()

val pics = input.toList.map(s => s.split(" -> ")).map(l => (l(0), l(1).split(",").toList))

val r = eurPath(pics.toMap.withDefaultValue(List.empty))

val str = r.slice(1, r.length).foldLeft(r(0))((v, s) => v + s(s.length - 1))

println(str)

def eurPath[T](graph: Map[T, List[T]])(implicit ord: Ordering[T]) = {

  val counts = new scala.collection.mutable.HashMap[T, Int].withDefaultValue(0)

  for (g <- graph;
       p <- g._2) {
    counts(g._1) += 1
    counts(p) -= 1
  }

  val unbs = counts.filter(_._2 != 0).keys.toList.sorted.reverse
  assert(unbs.size == 2)
  val mg = graph.updated(unbs.max, graph(unbs.max) ++ List(unbs.min))
  val c = eurCycle(mg)
  val i = c.sliding(2).zipWithIndex.find(_._1 == unbs).get._2
  val path = c.slice(i + 1, c.length) ++ c.slice(1, i + 1)
  path
}

def eurCycle[T](graph: Map[T, List[T]]) = {
  val size = graph.values.map(_.size).sum
  val e = refineWhile(List(graph.head._1, graph(graph.head._1)(0))) { cycle =>
    val cdjs = cycle.sliding(2).map(l => (l(0), l(1))).toList
    val c = graph(cycle.last).find(adj => !cdjs.contains((cycle.last, adj))) match {
      case Some(a) => cycle ++ List(a)
      case None =>
        assert(cycle.head == cycle.last, println(cycle.size, size))
        val a = cycle.find(a => graph(a).exists(o => !cdjs.contains((a, o)))).get
        val i = cycle.indexOf(a)
        val cc = cycle.slice(i, cycle.length) ++ cycle.slice(1, i + 1)
        cc
    }
    (c, cycle.length < size)
  }
  e ++ List(e(0))
}



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
