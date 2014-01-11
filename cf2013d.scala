
object Main {

  def main(args: Array[String]) = {
    val input = scala.io.Source.stdin.getLines()

    val n = input.next().toInt

    val used = new java.util.TreeSet[Int]()



    for (i <- 0 until n) {
      var exp = readInt()
      while (used.ceiling(exp) == exp) exp += 1
      used.add(exp)
      print(exp)
      if (i != n - 1) print(" ")
    }


  }
}
