import scala.collection.mutable

val input = scala.io.Source.fromFile("02.input").getLines()

val rm = Map(('A', 'T'), ('T', 'A'), ('C', 'G'), ('G', 'C'))

val gnome = input.next()

val output = gnome.reverse.map(rm(_))

print(output)


