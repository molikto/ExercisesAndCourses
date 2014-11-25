"String Composition Problem"

val input = scala.io.Source.fromFile("01.input").getLines()
val k = input.next().toInt
val gnome = input.next()

val r = gnome.sliding(k).toList.sorted

println(r.mkString("\n"))

