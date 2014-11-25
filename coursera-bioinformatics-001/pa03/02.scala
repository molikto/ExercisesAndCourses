"Median String Problem"

val input = scala.io.Source.fromFile("02.input").getLines()
val n = input.next().toInt
val dnas = input.toList

def score(p: String) = dnas.map(_.sliding(n).map(_.zip(p).count(pr => pr._1 != pr._2)).min).sum

val r = (0 until n).foldLeft(List(""))((li, _) => li.flatMap(l => "ACGT".map(l + _))).map(p => (p, score(p))).sortBy(_._2).head._1

println(r)

