"Generating Theoretical Spectrum Problem"

val input = scala.io.Source.fromFile("03.input").getLines()
val gnome = input.next()

val table = scala.io.Source.fromFile("im.table").getLines().map(s => {val a = s.split(" "); (a(0)(0), a(1).toInt)}).toMap

def mass(s: String) = s.foldLeft(0)((b, c) => b + table(c))
val a = (1 until gnome.size).map(len => (gnome + gnome.substring(0,len - 1)).sliding(len).map(s => (s, mass(s)))).flatten.+:(("", 0)).+:((gnome, mass(gnome))).map(_._2).toList.sorted

println(a.mkString(" "))

