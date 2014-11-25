"Contig Generation Problem"


val input = scala.io.Source.fromFile("05.input").getLines()

val reads = input.toList

val ps = reads.map(r => (r.init, r.tail))
val graph = ps.groupBy(_._1).map(p => (p._1, p._2.map(_._2)))
val graphInv = ps.groupBy(_._2).map(p => (p._1, p._2.map(_._1)))

println(graph)
val graphMap = graph.toMap.withDefaultValue(List.empty)
val graphMapInv = graphInv.toMap.withDefaultValue(List.empty)

val r = graph.withFilter(a => !(a._2.size == 1 && graphMapInv(a._1).size == 1)).flatMap(p => p._2.map(v => List(p._1, v))).map(p => {
  refineWhile(p) { l =>
    if (graphMap(l.last).size == 1 && graphMapInv(l.last).size == 1) {
      (l ++ List(graphMap(l.last)(0)), true)
    } else {
      (l, false)
    }
  }
})

val strs = r.map { l =>
  l.foldLeft(l(0).init)((l, s) => l + s.last)
}
println(strs.mkString("\n"))

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




