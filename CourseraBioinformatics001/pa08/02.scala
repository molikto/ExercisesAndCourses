"Number of Breakpoints Problem"

val input = scala.io.Source.fromFile("02.input").getLines()

val ns = input.next().drop(1).dropRight(1).split(" ").map(_.dropWhile(_ == '+').toInt).toIndexedSeq

val a = (List(0) ++ ns ++ List(ns.length + 1)).sliding(2).count(p => p(0) - p(1) != -1)

println(a)


