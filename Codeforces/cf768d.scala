import java.io._
import java.util
import java.util.Collections

import scala.collection.mutable
import scala.util._

object Main {

  def main(args: Array[String]) = {

    var debug: Object => Unit = null

    if (!args.isEmpty && args.head == "test") {
      debug = println
      val TEST = "2 100000 569\n605 986"

      System.setIn(new ByteArrayInputStream(TEST.getBytes))
    } else {
      debug = a => Unit
    }

    val input = scala.io.Source.stdin.getLines()


    val line1 = input.next().split(" ").map(_.toInt)
    val n = line1(0)
    val k = line1(1)
    val x = line1(2)

    var appear = new Array[Boolean](n)

    var original = input.next().split(" ").map(_.toInt).toBuffer
    var strengths = original.zipWithIndex


    var j = 0
    var allTrue = false
    while (j < k && !allTrue) {
      strengths = strengths.sortBy(_._1)
      var i = 0
      while (i < n) {
        strengths(i) = (strengths(i)._1 ^ x, strengths(i)._2)
        appear(strengths(i)._2) = true
        i += 2
      }
      val ss = strengths.map(_._1)
      val max = ss.max
      val min = ss.min
      allTrue = true
      for (i <- 0 until n) {
        if (appear(i)) {

        } else {
          val a1: Int = original(i)
          val a2: Int = a1 ^ x
          debug(a1.toString)
          debug(a2.toString)
          if (a1 <= max && a1 >= min && a2 <= max && a2 >= min) {
            appear(i) = true
          } else {
            allTrue = false
          }
        }
      }
      j += 1
    }

    val ss = strengths.map(_._1)
    val sol = ss.max + " " + ss.min

    println(sol)
  }
}
