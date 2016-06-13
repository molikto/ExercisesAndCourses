
object Main {

  def main(args: Array[String]) = {
    val input = scala.io.Source.stdin.getLines()
    val ps = input.next()
    val n  = input.next().toLong
    val qs = input.map(l => {
      val a = l.split(" ")
      (a(0).toLong, a(1).toLong)
    })
    val heis = new Array[Int](ps.length)
    heis(0) = 0
    for (i <- 1 until ps.length) {
      heis(i) = heis(0) + (if (ps(i) == '(') -1 else 1)
    }
    val ms = new Array[Int](ps.length)
    for (i <- 2 to ms.length) {

    }
  }
}
