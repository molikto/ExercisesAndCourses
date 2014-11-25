"Constructing Suffix Array Problem"

val input = scala.io.Source.fromFile("07.input").getLines()

val s = input.next()


def sufa(s: String) = (1 to s.length).map (i => (s.substring(s.length - i, s.length), i)).sortBy(_._1).map(s.length - _._2)


println(sufa(s).mkString(", "))
