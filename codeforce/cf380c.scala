
object Main {

  def main(args: Array[String]) = {
    val input = scala.io.Source.stdin.getLines()
    val m = input.next().toInt
    val mvs = (0 until m).map(_ => input.next().split(" ").map(_.toLong))
    val n = input.next().toLong
    val ns = input.next().split(" ").map(_.toLong)
    val sig = mvs.map(p => if (p(0) == 1) 1 else p(1) * p(2)).toArray
    val lens = new Array[Long](sig.length)
    lens(0) = sig(0)
    for (i <- 1 until lens.size) {
      lens(i) = lens(i - 1) + sig(i)
    }
    println(sig.mkString(" "))
    println(lens.mkString(" "))
    def nth(n: Long): Long = {
      // for example n == 1
      // then is of sig(0)(0)
      // the return is 0
      val i = java.util.Arrays.binarySearch(lens, n)
      val sign = if (i >= 0) i else -i-1
      assert(lens(sign) >= n, n.toString)
      val diff = n - (if (sign == 0) 0 else lens(sign-1))
      println(n + " " + i + " " + sign + " " + diff)
      if (mvs(sign)(0) == 2) {
        val l = mvs(sign)(1)
        nth({
          val a = diff % l
          if (a == 0) diff else a
        })
      } else {
        mvs(sign)(1)
      }
    }
    println(ns.map(nth).mkString(" "))
  }
}
