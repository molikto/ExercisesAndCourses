object Main {

  def main(args: Array[String]) = {
    val input = scala.io.Source.stdin.getLines()

    val n = input.next().toInt
    val a = input.next().split(" ").map(_.toInt).toArray

    val used = new java.util.TreeMap[Int, Int]()

    val r = new Array[Int](a.size)

    // this is a rather silly answer
    // you should zipWithIndex.sortBy(a._1)
    // the thing: use range tree
    // please never think by index!! they are names!!!
    for (i <- 0 until a.length) {
      val exp = a(i)
      val v = used.floorEntry(a(i))
      val (p , e) = if (v == null || v.getValue < exp) (exp, exp + 1) else (v.getValue, v.getValue + 1)
      r(i) = p
      if (used.containsKey(e)) {
        used.put(p, used.get(e))
        used.remove(e)
      } else
        used.put(p, e)
    }

    print(r.mkString(" "))
  }
}