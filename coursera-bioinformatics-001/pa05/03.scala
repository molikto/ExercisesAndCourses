"Universal String Problem"


val input = scala.io.Source.fromFile("03.input").getLines()

val k = 16


val max = math.pow(2, k - 1).toLong
println(max)
val graph = (0L until max).map(l => {
  (l, {
    val a = l * 2 % max
    List(a, a + 1)
  })
}).toMap

val r0 = eurCycle(graph)

println(r0)
val i0 = r0.indexOf(0)
val r1 = r0.slice(i0, r0.length) ++ r0.slice(0, i0)
val r = r1.slice(r1.length - k + 2, r1.length) ++ r1.slice(0, r1.length - k + 2)
println(r)

val str = r.foldLeft("")((s, l) => s + (l % 2))

println(str)

def eurPath[T](graph: Map[T, List[T]])(implicit ord: Ordering[T]) = {
  val counts = new scala.collection.mutable.HashMap[T, Int].withDefaultValue(0)
  for (g <- graph;
       p <- g._2) {
    counts(g._1) += 1
    counts(p) -= 1
  }

  val tp = counts.filter(_._2 != 0).keys.toList.sorted.reverse
  assert(tp.size == 2)
  val mg = graph.updated(tp.max, graph(tp.max) ++ List(tp.min))
  val c = eurCycle(mg)
  val i = c.sliding(2).zipWithIndex.find(_._1 == tp).get._2
  val path = c.slice(i + 1, c.length) ++ c.slice(1, i + 1)
  path
}
// use Long Map!!!
// use mutable list to remove the search everytime!!!
// list concatination is linear!!!

def eurCycle[T](graph0: Map[T, List[T]]) = {
  val graph = new scala.collection.mutable.HashMap[T, List[T]]()
  for (i <- graph0) graph += i
  val size = graph.values.map(_.size).sum
  val e = refineWhile(List({
    val a = graph(graph.head._1)(0)
    graph(graph.head._1) = graph(graph.head._1).drop(1)
    a
  }, graph.head._1)) { cycle =>
    val c = graph(cycle.head).headOption match {
      case Some(a) => {
        graph(cycle.head) = graph(cycle.head).filter(_ != a)
        a :: cycle
      }
      case None =>
        assert(cycle.head == cycle.last, println(cycle.size, size))
        val a = graph.find(p => !p._2.isEmpty && cycle.contains(p._1)).get._1
        val i = cycle.indexOf(a)
        cycle.slice(i, cycle.length) ++ cycle.slice(1, i + 1)
    }
    (c, c.length <= size)
  }
  e.reverse
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
