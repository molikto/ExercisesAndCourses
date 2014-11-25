"Peptide Encoding Problem"

val input = scala.io.Source.fromFile("02.input").getLines()
val gnome = input.next()
val pat = input.next()

val table = scala.io.Source.fromFile("rna_table").getLines().map(s => {val a = s.split(" "); (a(0).replaceAll("U", "T"), if (a.size > 1) a(1) else "")} ).toMap

def reverse(gnome: String) = {
  val rm = Map(('A', 'T'), ('T', 'A'), ('C', 'G'), ('G', 'C'))
  gnome.reverse.map(rm(_))
}

val a = gnome.sliding(pat.size * 3).filter { a =>
  def prid(s: String) = s.grouped(3).map(s => if (table.contains(s)) table(s) else "").mkString == pat
  prid(a) || prid(reverse(a))
}

println(a.mkString("\n"))



