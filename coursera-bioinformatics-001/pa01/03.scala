

val input = scala.io.Source.fromFile("03.input").getLines()
val pattern = input.next()
val gnome = input.next()

val a = gnome.sliding(pattern.size).zipWithIndex.filter(_._1 == pattern).map(_._2)

println(a.mkString(" "))

