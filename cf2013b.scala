
object Main {

  def main(args: Array[String]) = {
    val input = scala.io.Source.stdin.getLines()

    val n = input.next().toInt
    val a = input.next().split(" ").map(_.toInt).toList.toArray

    var used = scala.collection.mutable.TreeSet.empty[Int]

    val v = new Array[Int](a.size)

    for (i <- 0 until a.length) {
      var exp = a(i)
      val range = used.from(exp).takeWhile(i => if (i == exp) {exp += 1; true} else false)

    }
  }
}
