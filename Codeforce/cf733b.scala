
object Main {

  def main(args: Array[String]) = {
    val input = scala.io.Source.stdin.getLines()
    input.next()
    val lrs = input.toSeq.map(line => line.split(' ').map(_.toInt))
    val lness = lrs.map(arr => arr(0) - arr(1)).sum
    val change = lrs.map(arr => (lness + arr(1) * 2 - arr(0) * 2).abs).zipWithIndex.maxBy(_._1)
    val k = if (change._1 > lness.abs) change._2 + 1 else 0
    print(k)
  }
}
