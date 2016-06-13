"De Bruijn Graph from k-mers Problem"


val input = scala.io.Source.fromFile("04.input").getLines()
val kmers = input.toList
val k = kmers(0).length


val r = kmers.map(p => p.sliding(k - 1).toList).map(l => (l(0), l(1))).groupBy(_._1)
  .map(p => (p._1, p._2.unzip._2.filter(_.startsWith(p._1.substring(1, p._1.length - 1))).sorted.distinct)).toList.sortBy(_._1)

println(r.map(p => p._1 + " -> " + p._2.mkString(",")).mkString("\n"))

