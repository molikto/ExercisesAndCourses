"Overlap Graph Problem"


val input = scala.io.Source.fromFile("02.input").getLines()
val dnas = input.toList

val r = for (
  l <- dnas;
  r <- dnas;
  val pa = (l, r)
  if l.substring(0, l.length - 1) == r.substring(1, r.length)
) yield pa

println(r.map(p => p._2 + " -> " + p._1).mkString("\n"))

