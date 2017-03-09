object Main {

  def main(args: Array[String]) = {
    val input = scala.io.Source.stdin.getLines()

    val n = input.next().toInt
    val a = input.next().split(" ").map(_.toInt).toArray

    val used = new java.util.TreeMap[Int, Int]()

    val r = new Array[Int](a.size)

    for (i <- 0 until a.length) {
      val exp = a(i)
      val v = used.floorEntry(exp)
      if (v == null || v.getValue < exp) {
        r(i) = exp
        if (used.containsKey(exp + 1)) {
          used.put(exp, used.get(exp + 1))
          used.remove(exp + 1)
        } else
          used.put(exp, exp + 1)
      } else {
        r(i) = v.getValue
        if (used.containsKey(v.getValue + 1)) {
          used.put(v.getKey, used.get(v.getValue + 1))
          used.remove(v.getValue + 1)
        } else {
          used.put(v.getKey, v.getValue + 1)
        }
      }
    }

    print(r.mkString(" "))
  }
}