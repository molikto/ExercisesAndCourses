
object Main {

  def line = readLine()

  def main(args: Array[String]) = {
    val Array(n, d) = line.split(" ").map(_.toInt)
    val as = line.split(" ").map(_.toInt)
    val m = line.toInt
    val r = as.sorted.take(m).sum - d * (m - n).max(0)
    println(r)
  }
}

