
object Main {

  def main(args: Array[String]) = {
    val input = scala.io.Source.stdin.getLines()
    val List(k, x, n, m) = input.next().split(" ").map(_.toInt).toList
    /*

    val sc "start with c" = Array[Boolean](k)
    val ea "end with a" = Array[Boolean](k)

    val acn "ac number" = Array[Int](k)

    acn[n] = [sc[n-1] && ea[n-2]] + acn[n-1] + acn[n-2]
    sc[n] = sc[n-2] = n % 2 == 0 ? sc[2] else sc[1]
    ea[n] = ea[n-1] = ... ea[2] | if n == 1 ea[1]


    if (sc[1] && sc[2] && ea[2] && ea[1])
      acn[n] = 1 + acn[n-1] + acn[n-2]


      n + a n-1 = c(n-1 + a n-2)
      n a-c -ac
      1 -1 -1

      I do not think it will work...
     */
    val res = new Array[Int](k)
    // sc1 sc2 ea2 ea1
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
                      // what is () + + !!!
                      res(n) = (if (ea1 && (if (n % 2 == 0) sc0 else sc1)) 1 else 0) + res(n-1) + res(n-2)
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
