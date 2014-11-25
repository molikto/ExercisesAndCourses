"De Bruijn Graph from a String Problem"


val input = scala.io.Source.fromFile("03.input").getLines()
val k = input.next().toInt
val gnome = input.next()

val r = gnome.sliding(k - 1).sliding(2).map(l => (l(0), l(1))).toList.groupBy(_._1)
  .map(p => (p._1, p._2.unzip._2.filter(_.startsWith(p._1.substring(1, k - 1))).sorted)).toList.sortBy(_._1)

println(r.map(p => p._1 + " -> " + p._2.mkString(",")).mkString("\n"))

