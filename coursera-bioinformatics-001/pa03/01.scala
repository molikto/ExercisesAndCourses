"Motif Enumeration"

val input = scala.io.Source.fromFile("01.input").getLines()
val kd = input.next().split(" ").map(_.toInt)
val k = kd(0)
val d = kd(1)
val dnas = input.toList

def gen(s: String, n: Int): List[String] = n match {
  case 0 => List(s)
  case np =>
    val r = for (
      g <- gen(s, np-1);
      m <- 0 until g.length;
      p <- "ATGC") yield {
      val a = g.toCharArray
      a(m) = p
      String.copyValueOf(a)
    }
    r.distinct
}

val r = (for (
  dna <- dnas;
  pic <- dna.sliding(k);
  modif <- gen(pic, d)
  if dnas.forall(_.sliding(k).exists(_.zip(modif).count(p => p._1 != p._2) <= d ))
) yield modif).distinct

println(r.mkString(" "))
