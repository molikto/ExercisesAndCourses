import java.io.ByteArrayInputStream

object Main {

  def main(args: Array[String]) = {
    /** setup **/
    var debug: Object => Unit = null
    if (!args.isEmpty && args.head == "test") {
      debug = println
      val TEST = "2 100000 569\n605 986"
      System.setIn(new ByteArrayInputStream(TEST.getBytes))
    } else {
      debug = a => Unit
    }
    val input = scala.io.Source.stdin.getLines()

    /** parse **/

    val line = input.next().split(" ").map(_.toInt)
    val (n, m, k) = (line(0), line(1), line(2))

    var sol = ""
    val p1 = "Timur"
    val p2 = "Marsel"
    /** algorithm **/

    if (n % 2 == 0) sol = p2
    else if (k == m) sol = p2
    else if (k != 0 && k != 1 && (2 to (Math.ceil(Math.sqrt(m)).toInt)).find(i => m % i == 0 && m / i >= k).isEmpty) sol = p2
    else sol = p1
    /** print **/
    println(sol)
  }
}