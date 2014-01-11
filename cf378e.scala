object Main {
  def main(args: Array[String]) = {
    val input = scala.io.Source.stdin.getLines()
    val List(n, k) = input.next().split(" ").map(_.toInt).toList
    val ys = input.map(_.split(" ").map(_.toInt)).toList
    ys.foldLeft((List((0.0, 0.0), (0.0, k.toDouble)), List.empty[Double])) { (res, y) =>
      val poly = res._1
      val area = res._2
      val xs = (poly.map(_._1) ++ (0 to k)).distinct.sorted
      (null, null)
    }
  }
}
