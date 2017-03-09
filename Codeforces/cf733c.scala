
import java.io._

import scala.util._

object Main {

  def main(args: Array[String]) = {

    var debug: Object => Unit = null

    if (!args.isEmpty && args.head == "test") {
      debug = println
      val TEST =
        """
          |2
          |1 2
          |2
          |3 1
        """.stripMargin.trim

      System.setIn(new ByteArrayInputStream(TEST.getBytes))
    } else {
      debug = a => Unit
    }

    val input = scala.io.Source.stdin.getLines()


    val n = input.next().toInt
    val a = input.next().split(' ').toSeq.map(_.toLong)
    val k = input.next().toInt
    val b = input.next().split(' ').toSeq.map(_.toLong)
    val res = Try {
      assert(a.sum == b.sum)
      val f = a.foldLeft(Seq.empty[Seq[Long]]) { (l, i) =>
        if (l.isEmpty || l.head.sum == b(l.length - 1)) {
          assert (i <= b(l.length))
          Seq(i) +: l
        } else if (l.head.sum < b(l.length - 1)) {
          assert (l.head.sum + i <= b(l.length - 1))
          (i +: l.head) +: l.tail
        } else {
          throw new Exception()
        }
      }
      val g = f.map(_.reverse).reverse
      assert(g.forall(l => l.size == 1 || !l.forall(_ == l.head)))
      g.zipWithIndex.filter(_._1.size > 1).flatMap(pair => {
        val k = pair._1
        val base = pair._2 + 1
        val max = k.max
        val notMaxIndex = k.indexWhere(_ != max)
        val nearestMaxIndex = k.zipWithIndex.filter(pair => pair._1 == max).minBy(a => (a._2 - notMaxIndex).abs)._2
        if (nearestMaxIndex > notMaxIndex) {
          (0 until nearestMaxIndex).reverse.map(a => (a + base + 1, 'L')) ++ (0 until (k.length - nearestMaxIndex - 1)).map(_ => (base, 'R'))
        } else {
          (0 until (k.length - nearestMaxIndex - 1)).map(_ => (nearestMaxIndex + base, 'R')) ++ (0 until nearestMaxIndex).reverse.map(a => (a + base + 1, 'L'))
        }
      })
    }
    val sol = res match {
      case Success(a) => "YES" + a.map(b => "\n" + b._1 + " " + b._2).mkString
      case Failure(e) => "NO"
    }



    println(sol)
  }
}

