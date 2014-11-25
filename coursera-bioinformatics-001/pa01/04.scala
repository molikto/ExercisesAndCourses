

val input = scala.io.Source.fromFile("04.input").getLines()
val gnome = input.next()
val klt = input.next().split(" ").map(_.toInt)
val k = klt(0)
val l = klt(1)
val t = klt(2)

val a = gnome.sliding(k).zipWithIndex.toList.groupBy(_._1).map(a => (a._1, a._2.map(_._2).sorted))
  .filter(_._2.sliding(t).exists(w => w.size == t && w.last - w.head + k <= l)).map(_._1).toList.sorted


println(a.mkString(" "))


