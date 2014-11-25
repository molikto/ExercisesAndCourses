
object Main {

  def main(args: Array[String]) = {
    val input = scala.io.Source.stdin.getLines()
    val (n, k) = {
      val a = input.next().split(" ").map(_.toInt)
      (a(0), a(1))
    }
    val roads = (0 to (n - 1)).map(_ => {
      val a = input.next().split(" ").map(_.toInt)
      (a(0), a(1))
    })
  }
}
