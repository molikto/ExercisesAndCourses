"Spectral Convolution Problem"


val input = scala.io.Source.fromFile("06.input").getLines()
val spec = input.next().split(" ").map(_.toInt).toList

val r = (for (a <- spec; b<- spec) yield (a - b).abs).filter(_ != 0).sorted.zipWithIndex.withFilter(_._2 % 2 == 0).map(_._1)

println(r.mkString(" "))



