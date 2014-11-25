"Profile-most Probable k-mer Problem"

val input = scala.io.Source.fromFile("03.input").getLines()
val text = input.next()
val k = input.next().toInt
input.next()
val mat = input.toList.map(_.split(" ").map(_.toFloat))
val table = Map('A'-> 0, 'C' -> 1, 'G' -> 2, 'T' -> 3)



val r = text.sliding(k).maxBy(s => s.zipWithIndex.foldLeft(1f)((p, c) => p * mat(c._2)(table(c._1))))

println(r)