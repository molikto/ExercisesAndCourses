"Protein Translation Problem"

val input = scala.io.Source.fromFile("01.input").getLines()
val gnome = input.next()

val table = scala.io.Source.fromFile("rna_table").getLines().map(s => {val a = s.split(" "); (a(0), if (a.size > 1) a(1) else "")} ).toMap

val a = gnome.grouped(3).map(table)


println(a.mkString)


