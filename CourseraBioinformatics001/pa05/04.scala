"String Construction from Read-Pairs Problem"


val input = scala.io.Source.fromFile("04.input").getLines()

val d = input.next().toInt

val pairs = input.toList.map(_.split("\\|"))

val graph = pairs.map(p => {
  val a = p(0)
  val b = p(1)
  ((a.init, b.init), (a.tail, b.tail))
}).groupBy(_._1).map(p => (p._1, p._2.map(_._2))).toMap

val ir = {
  val a = eurPath(graph).unzip
  a._1 ++ a._2.slice(a._2.length - d - a._2(0).length - 1, a._2.length)
}

val str = ir(0).init + ir.map(_.last).mkString
println(str)

def eurPath[T](graph: Map[T, List[T]])(implicit ord: Ordering[T]) = {
  val counts = new scala.collection.mutable.HashMap[T, Int].withDefaultValue(0)
  for (g <- graph;
       p <- g._2) {
    counts(g._1) += 1
    counts(p) -= 1
  }
  val tp = counts.filter(_._2 != 0).toList.sortBy(_._2).unzip._1
  assert(tp.size == 2)
  val mg = graph.updated(tp(0), graph.getOrElse(tp(0), List.empty) ++ List(tp(1)))
  val c = eurCycle(mg)
  val i = c.sliding(2).zipWithIndex.find(_._1 == tp).get._2
  val path = c.slice(i + 1, c.length) ++ c.slice(0, i + 1)
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
      case Some(a) =>
        graph(cycle.head) = graph(cycle.head).filter(_ != a)
        a :: cycle
      case None =>
        assert(cycle.head == cycle.last, println(cycle.size, size))
        val a = graph.find(p => !p._2.isEmpty && cycle.contains(p._1)).get._1
        val i = cycle.indexOf(a)
        // to be efficient, there should be O(1) algorithms
        cycle.slice(i, cycle.length) ++ cycle.slice(1, i + 1)
    }
    (c, c.length <= size)
  }
  e.reverse
}

def refineWhile[T](init: T)(body: T => (T, Boolean)) = {
  var now = init
  var c = true
  while(c) {
    val r = body(now)
    c = r._2
    if (c) now = r._1
  }
  now
}
