
object Main {

  import scala.collection.mutable
  val lines = scala.io.Source.stdin.getLines()
  def line = lines.next()

  def main(args: Array[String]) = {
    val Array(n, m) = line.split(" ").map(_.toInt + 1)
    val as = line.split(" ").map(_.toInt)
    val ls = lines.toList.map(_.toInt - 1)
    val cs = {
      val cs = new Array[Int](as.length)
      val map = new mutable.HashMap[Int, Int].withDefaultValue(0)
      for (i <- -n until as.length) {
        if (i >= 0) map(as(i)) = map(as(i)) - 1
        if (i + n < as.length) map(as(i + n)) = map(as(i + n)) + 1
        if (i >= 0 && i < as.length) cs(i) = map.values.count(_ != 0)
      }
      println(cs.mkString(" "))
      cs
    }
    val r = ls.map(l => cs(l))
    println(r.mkString("\n"))
  }
}

