

object Main {

  def line = readLine()

  case class Match(ans: Int, cip: Int, score: Int)
  case class State(score: Int, matchs: List[Match])


  def dp(remain: Stream[Match], current: State, max: State, k: Int): State = {
    if (remain.take(k).map(_.score).sum + current.score <= max.score) {
      max
    } else {
      val h = remain.head
      val nc = State(current.score + h.score, h +: current.matchs)
      // YOU SHOULD BE GREEDY HERE, FILTER RESULTS NOT USED
      val dp2 = dp(remain.tail.filter(a => a.ans != h.ans && a.cip != h.cip), nc, if (nc.score > max.score) nc else max, k - 1)
      // YOU SHOULD BE GREEDY HERE
      val nmax = if (dp2.score > max.score) dp2 else max
      dp(remain.tail, current, nmax, k)
    }
  }
  def main(args: Array[String]) = {
    val Array(n, k) = line.split(" ").map(_.toInt)
    val cip = line
    val ans = line


    val ff = {
      val a = new Array[Int](k)
      var i = 0
      while (i < ans.length) {
        a(ans(i) - 'a') += 1
        i += 1
      }
      a
    }

    val ss = {
      val s = new Array[Array[Int]](k)
      for (i <- 0 until k) {
        s(i) = new Array[Int](k)
      }
      var i = 0
      while (i < n) {
        s(ans(i) - 'a')(cip(i) - 'a') += 1
        i += 1
      }
      s
    }


    val mm = ss.zipWithIndex.flatMap { p =>
      val cs = p._1
      val ia = p._2
      cs.zipWithIndex.map(q => Match(ia, q._2, q._1))
    }.sortBy(-_.score).toList


    val aaa = dp(mm.toStream, State(0, List.empty), State(0, List.empty), k)
    val s = {
      val vvv = aaa.matchs.sortBy(_.cip).map(_.ans)
      if (vvv.size < k) {
        vvv ++ ((0 until k).toSet -- vvv)
      } else vvv
    }
    println(aaa.score)
    println(s.map(i => (i + 'a').toChar).mkString)
  }
}


