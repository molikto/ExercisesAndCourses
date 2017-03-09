import java.io.ByteArrayInputStream

import scala.collection.mutable

object Main {

  var dddd = false

  def ptime() = if (dddd) println(System.currentTimeMillis())
  def delog(a: String) = if (dddd) println(a)

  def main(args: Array[String]) = {
    /** setup **/
    var debug: Object => Unit = null
    if (!args.isEmpty && args.head == "test") {
      dddd = true
      debug = println
      val TEST =
        """17
          |25 29 37 207 122 189 118 42 54 95 154 160 162 225 228 237 248
          |19
          |25 29 248 37 147 209 42 54 255 95 154 160 162 225 228 237 73 248 10
        """.stripMargin
      System.setIn(new ByteArrayInputStream(TEST.getBytes))
    } else {
      debug = a => Unit
    }
    val input = scala.io.Source.stdin.getLines()

    /** parse **/ ptime()

    input.next()
    val q1 = input.next().split(" ").map(_.toInt).toSeq.toArray[Int]
    input.next()
    val q2 = input.next().split(" ").map(_.toInt).toSeq.toArray[Int]

    /** algorithm **/ ptime()


    final case class Case(_1: Int, _2: Seq[Int])
    val dp = Array.ofDim[Case](q1.length, q2.length)
    var max = Case(0, Seq.empty[Int])
    for (i1 <- q1.indices) {
      for (i2 <- q2.indices) {
        if (q1(i1) == q2(i2)) {
          val a = q1(i1)
          var m = Case(1, List(a): Seq[Int])
          for (k1 <- 0 until i1) {
            for (k2 <- 0 until i2) {
              if (q1(k1) == q2(k2) && q1(k1) < a) {
                val dd = dp(k1)(k2)
                if (dd._1 + 1> m._1) {
                  m = Case(dd._1 + 1, a +: dd._2)
                }
              }
            }
          }
          dp(i1)(i2) = m
          if (m._1 > max._1) max = m
        }
      }
    }

    val sol = max._2.reverse


    //    val t1 = q1.zipWithIndex.sortBy(_._1)
    //    val t2 = q2.zipWithIndex.sortBy(_._1)
    //
    //    val b = mutable.ArrayBuffer.empty[(Int, Seq[Int], Seq[Int])]
    //
    //    var i1 = 0
    //    var i2 = 0
    //    while (i1 < t1.size && i2 < t2.size) {
    //      val a1 = t1(i1)
    //      val a2 = t2(i2)
    //      if (a1._1 < a2._1) i1 += 1
    //      else if (a2._1 < a1._1) i2 += 1
    //      else {
    //        val a = a1
    //        var i1e = i1
    //        while (i1e < t1.size && t1(i1e)._1 == a._1) i1e += 1
    //        var i2e = i2
    //        while (i2e < t2.size && t2(i2e)._1 == a._1) i2e += 1
    //        b.append((a1._1, (i1 until i1e).map(a => t1(a)._2), (i2 until i2e).map(a => t2(a)._2)))
    //        i1 = i1e
    //        i2 = i2e
    //      }
    //    }
    //
    //    ptime()
    //    var indexes = mutable.HashMap.empty[(Int, Int), Seq[(Int, Int)]]
    //
    //    for (fi <- b.indices.reverse) { // considering including the element i
    //    val f = b(fi)
    //      val prev = indexes
    //      var max = if(prev.isEmpty) 0 else prev.values.maxBy(_.size).size
    //      var n = prev.clone()
    //      for (seq <- prev.values) {
    //        val h = seq.head
    //        if (seq.size + fi + 1 > max) {
    //          if (h._1 + seq.size < max || h._2 + seq.size < max) {
    //            n -= h
    //          } else {
    //            var added = false
    //            for (ia <- f._2.reverse) {
    //              for (ib <- f._3.reverse) {
    //                if (!added && ia < h._1 && ib < h._2) {
    //                  val kk = (ia, ib)
    //                  val b = kk +: seq
    //                  val psize = n.get(kk).map(_.size).getOrElse(0)
    //                  if (b.size > psize) {
    //                    if (b.size > max) max = b.size
    //                    n += kk -> b
    //                    if (ia == h._1 - 1 && ib == h._2 - 1) { // this new thing is strictly better than the old one
    //                      n -= h
    //                    }
    //                    added = true
    //                  }
    //                }
    //              }
    //            }
    //          }
    //        } else {
    //          n -= h
    //        }
    //      }
    //      val kk = (f._2.last, f._3.last)
    //      if (max <= fi && max <= kk._1 && max <= kk._2) {
    //        if (n.get(kk).isEmpty) n += kk -> Seq(kk)
    //      }
    //      indexes = n
    //    }

    //    val t = if (indexes.isEmpty) Seq.empty else indexes.values.maxBy(_.size)
    //    val sol = t.map(i => q1(i._1))

    /** print **/ ptime()

    println(sol.length + "\n" + sol.mkString(" "))
  }
}