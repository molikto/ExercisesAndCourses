import java.io.ByteArrayInputStream

import scala.collection.mutable

object Main {

  var dddd = false

  def ptime() = if (dddd) println(System.currentTimeMillis())
  def delog(a: String) = if (dddd) println(a)

  /**
    * lessions:
    *
    * think about the algorithm
    * hashset is slow
    * buffer printlns
    *
    *
    *
    */
  def main(args: Array[String]) = {
    /** setup **/
    var debug: Object => Unit = null
    if (!args.isEmpty && args.head == "test") {
      dddd = true
      debug = println
      val TEST =
        """2
          |1 1
          |0
        """.stripMargin
      System.setIn(new ByteArrayInputStream(TEST.getBytes))
    } else {
      debug = a => Unit
    }
    val input = scala.io.Source.stdin.getLines()

    /** parse **/ ptime()

    input.next()
    val ps = input.next().split(" ").map(_.toInt)
    val qs = input.next().split(" ").drop(1).map(_.toInt - 1)
    //    val ps = (0 until 10000).map(i => 1)
    //    val qs = Set.empty[Int]

    /** algorithm **/ ptime()

    /// copied... still don't know why greedy works

    qs.foreach(q => {
      val p = ps(q)
      ps(q) = -p
    })


    var i = ps.size - 1
    var stack = List.empty[Int]

    while (i >= 0) {
      val p = ps(i)
      if (p < 0) {
        stack = p +: stack
        i = i - 1
      } else {
        val valid = stack.nonEmpty && stack.head == -p
        if (valid) {
          stack = stack.tail
          i = i - 1
        } else {
          stack = -p +: stack
          ps(i) = -p
          i = i - 1
        }
      }
    }

    val sol = if (stack.isEmpty) {
      Some(ps)
    } else {
      None
    }

    //    val sol = found.map(list => {
    //      val m = list.toSet
    //      ps.zipWithIndex.map(pair => if (m(pair._2)) -pair._1 else pair._1)
    //    })


    //// non-recursive one... but didn't not work, I need to know why the greedy one worked
    //    var i = 0
    //    var stack: Seq[Int] = List.empty
    //    var comm: Seq[Int] = List.empty
    //    var alternatives: Seq[() => Unit] = List.empty
    //
    //    var found: Option[Seq[Int]] = None
    //    var finished = false
    //
    //
    //    def goBack() = {
    //      if (alternatives.isEmpty) {
    //        finished = true
    //      } else {
    //        val head = alternatives.head
    //        alternatives = alternatives.tail
    //        head()
    //      }
    //    }
    //    while (!finished) {
    //      while (i < ps.size) {
    //        if (stack.size > ps.size - i) {
    //          goBack()
    //        } else {
    //          val p = ps(i)
    //          val q = qs(i)
    //          if (q) {
    //            if (stack.nonEmpty && p == stack.head) {
    //              comm = i +: comm
    //              i = i + 1
    //              stack = stack.tail
    //            } else {
    //              goBack()
    //            }
    //          } else {
    //            val valid = stack.nonEmpty && p == stack.head
    //            if (valid) {
    //              val cc = comm
    //              val ss = p +: stack
    //              val ii = i + 1
    //              alternatives = (() => {
    //                comm = cc
    //                stack = ss
    //                i = ii
    //              }) +: alternatives
    //              comm = i +: comm
    //              i = i + 1
    //              stack = stack.tail
    ////              val cc = i +: comm
    ////              val ii = i + 1
    ////              val ss = stack.tail
    ////              alternatives = (() => {
    ////                i = ii
    ////                stack = ss
    ////                comm = cc
    ////              }) +: alternatives
    //            }
    //            i = i + 1
    //            stack = p +: stack
    //          }
    //        }
    //      }
    //      if (stack.isEmpty) {
    //        found = Some(comm)
    //        finished = true
    //      } else {
    //        goBack()
    //      }
    //    }
    //
    //


    //// recursive one, did not work

    //    def rec(i: Int, stack: Seq[Int], comm: Seq[Int]): Option[Seq[Int]] = {
    //      if (i == ps.length) {
    //        if (stack.isEmpty) Some(comm)
    //        else None
    //      } else {
    //        val p = ps(i)
    //        val q = qs(i)
    //        if (q) {
    //          if (stack.nonEmpty && p == stack.head) {
    //            rec(i + 1, stack.tail, i +: comm)
    //          } else {
    //            None
    //          }
    //        } else {
    //          val valid = stack.nonEmpty && p == stack.head
    //          if (valid) {
    //            val k = rec(i + 1, stack.tail, i +: comm)
    //            if (k.nonEmpty) return k
    //          }
    //          rec(i + 1, p +: stack, comm)
    //        }
    //      }
    //    }

    /** print **/ ptime()

//    if (sol.isEmpty) {
//      println("NO")
//    } else {
//      println("YES")
//      val list = sol.get
//      var i = 0
//      while (i < list.size - 1) {
//        print(list(i))
//        print(" ")
//        i += 1
//      }
//      println(list.last)
//    }
    println(sol.map(list => "YES\n" + list.mkString(" ")).getOrElse("NO"))
  }

}