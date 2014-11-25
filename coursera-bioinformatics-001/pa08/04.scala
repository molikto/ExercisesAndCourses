"Shared k-mers Problem"

val input = scala.io.Source.fromFile("04.input").getLines()



val k = input.next().toInt
val table = Map('A' -> 'T', 'T' -> 'A', 'C' -> 'G', 'G' -> 'C')
val s1, s2 = input.next()

import scala.collection.mutable

val m = mutable.Map.empty[String, List[(Int, Int)]].withDefaultValue(List.empty)

List(s1, s2).zipWithIndex.foreach { p =>
  val s = p._1
  val i = p._2
  s.sliding(k).zipWithIndex.foreach { p =>
    m(p._1.map(table)) = m(p._1.map(table)).::((i, p._2))
    m(p._1.map(table).reverse) = m(p._1.map(table).reverse).::((i, p._2))
    m(p._1) = m(p._1).::((i, p._2))
    m(p._1.reverse) = m(p._1.reverse).::((i, p._2))
  }
}

val r = (for (l <- m.values; a <- l; b <- l; if a._1 < b._1) yield (a._2, b._2)).toList.distinct

println(r.map(a => "(" + a._1.toString + ", " + a._2.toString + ")").mkString("\n"))


