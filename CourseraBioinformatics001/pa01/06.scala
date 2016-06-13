

val input = scala.io.Source.fromFile("06.input").getLines()
val pattern = input.next()
val gnome = input.next()
val n = input.next().toInt

val a = gnome.sliding(pattern.size).zipWithIndex.filter(_._1.zip(pattern).foldLeft(0)((i, p) => if (p._1 == p._2) i else i + 1) <= n).map(_._2)

println(a.mkString(" "))




