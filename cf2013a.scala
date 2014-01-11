
object Main {

  def main(args: Array[String]) = {
    val input = scala.io.Source.stdin.getLines()

    val (a, b) = {
      val a = input.next().split(" ").map(_.toInt)
      (a(0), a(1))
    }

    var c = a
    var s = 0
    var r = 0
    while (c > 0) {
      s += c
      r += c
      c = r / b
      r = r % b
    }

    print(s)
  }
}
