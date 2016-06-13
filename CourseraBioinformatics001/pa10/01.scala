"Burrows-Wheeler Transform Construction Problem"

val input = scala.io.Source.fromFile("01.input").getLines()


val s = input.next()


val res = (0 until s.length).map(i => s.substring(i, s.length) + s.substring(0, i)).sorted.map(_.last)

println(res.mkString)


