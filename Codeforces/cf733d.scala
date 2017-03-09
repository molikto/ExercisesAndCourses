
import java.io._

import scala.util._

object Main {

  def main(args: Array[String]) = {

    var debug: Object => Unit = null

    if (!args.isEmpty && args.head == "test") {
      debug = println
      val TEST =
        """
          |7
          |10 7 8
          |5 10 3
          |4 2 6
          |5 5 5
          |10 2 8
          |4 2 1
          |7 7 7
          |""".stripMargin.trim

      System.setIn(new ByteArrayInputStream(TEST.getBytes))
    } else {
      debug = a => Unit
    }

    val input = scala.io.Source.stdin.getLines()


    input.next()
    val ps = input.toSeq.map(_.split(' ').map(_.toInt).toSeq.sorted)

    val hash = scala.collection.mutable.HashMap.empty[(Int, Int), Seq[Int]]

    def put(k: (Int, Int), p: Int) = {
      hash.get(k) match {
        case None => hash.put(k, Seq(p))
        case Some(a) =>
          if (a.length >= 2) {
            if (a(0) < p) hash.put(k, Seq(p, a(0)))
            else if (a(1) < p) hash.put(k, Seq(a(0), p))
          } else if (a.length == 1) {
            if (a(0) < p) hash.put(k, Seq(p, a(0)))
            else hash.put(k, Seq(a(0), p))
          }
      }
    }
    for (p <- ps) {
      val k1 = (p(0), p(1))
      val p1 = p(2)
      val k2 = (p(0), p(2))
      val p2 = p(1)
      val k3 = (p(1), p(2))
      val p3 = p(0)
      put(k1, p1)
      if (k2 != k1) put(k2, p2)
      if (k3 != k1 && k3 != k2) put(k3, p3)
    }

    val pair = hash.maxBy(e=> Seq(e._1._1, e._1._2, e._2.sum).min)

    val first = ps.indexOf(Seq(pair._1._1, pair._1._2, pair._2(0)).sorted)

    debug(pair)
    debug(Seq(pair._1._1, pair._1._2, pair._2(0)).mkString)
    debug(first + "")

    val sol = if (pair._2.size == 2) {
      val second = ps.zipWithIndex.find(p => p._1 == Seq(pair._1._1, pair._1._2, pair._2(1)).sorted && p._2 != first).get._2
      "2\n" + (first + 1) + " " + (second + 1)
    } else {
      "1\n" + (first + 1)
    }

    println(sol)
  }
}

