

val input = scala.io.Source.fromFile("05.input").getLines()
val gnome = input.next()

val arr = new Array[Int](gnome.size+1)
arr(0) = 0
for (i <- 0 until gnome.size) {
  arr(i + 1) = arr(i) + (gnome(i) match {
    case 'C' => -1
    case 'G' => 1
    case _ => 0
  })
}

var a = arr.zipWithIndex.groupBy(_._1).toList.sortBy(_._1).head._2.map(_._2).sorted

println(a.mkString(" "))

