
object Main {

  def main(args: Array[String]) = {
    val input = scala.io.Source.stdin.getLines()
    val List(k0, x0, n0, m0) = input.next().split(" ").toList
    val k = k0.toInt
    val x = x0.toLong
    val n = n0.toInt
    val m = m0.toInt
    val res = new Array[Long](k)
    for (sc0 <- List(true, false))
      for (sc1 <- List(true, false))
        for (ea0 <- List(true, false))
          for (ea1 <- List(true, false)) {
            if (n > 1 || !(sc0 && ea0))
              if (m > 1 || !(sc1 && ea1)) {
                def calmax(sc: Boolean, ea: Boolean, len: Int) = {
                  var a = len
                  if (sc) a-= 1
                  if (ea) a-= 1
                  a.max(0) / 2
                }
                val acn0max = calmax(sc0, ea0, n)
                val acn1max = calmax(sc1, ea1, m)
                for (acn0 <- 0 to acn0max)
                  for (acn1 <- 0 to acn1max) {
                    res(0) = acn0
                    res(1) = acn1
                    for (n <- 2 until res.length) {
                      res(n) = (if ((if (n == 2) ea0 else ea1) && (if (n % 2 == 0) sc1 else sc0)) 1 else 0) + res(n-1) + res(n-2)
                    }
                    if (res.last == x) {
                      val s0 =
                          (if (sc0) "C" else "") + (0 until acn0).map(_ => "AC").mkString + (0 until (n - (if (sc0) 1 else 0) - (if (ea0) 1 else 0) - acn0 * 2)).map(_ => "X").mkString + (if (ea0) "A" else "")
                      val s1 =
                          (if (sc1) "C" else "") + (0 until acn1).map(_ => "AC").mkString + (0 until (m - (if (sc1) 1 else 0) - (if (ea1) 1 else 0) - acn1 * 2)).map(_ => "X").mkString + (if (ea1) "A" else "")
                      println(s0)
                      println(s1)
                      System.exit(0)
                    }
                  }
              }
          }
    print("Happy new year!")
  }
}
