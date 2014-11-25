"Inverse Burrows-Wheeler Transform Problem"

val input = scala.io.Source.fromFile("02.input").getLines()

val s = input.next().zipWithIndex.toList

val a = s.zip(s.sorted).toMap

val res0 = (1 until s.length).foldLeft(List(('$', s.find(_._1 == '$').get._2))) { (l, _) => a(l.head) :: l }.map(_._1)

val res = res0.reverse.drop(1).mkString + "$"

println(res)
