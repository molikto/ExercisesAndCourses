object Main {
  def main(args: Array[String]) = {
    val input = scala.io.Source.stdin.getLines()
    val List(n, k) = input.next().split(" ").map(_.toInt).toList
    val ys = input.map(_.split(" ").map(_.toInt)).toList
    val res = ys.zipWithIndex.sliding(2).foldLeft(((0 until k).map(i => List((0, i), (0, i + 1))).toArray,
      List.empty[Int])) { (p, l) =>
      val poly = p._1
      val areas = p._2
      p
    }._2
  }
}
