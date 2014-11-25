

val input = scala.io.Source.fromFile("07.input").getLines()
val parts = input.next().split(" ")
val gnome = parts(0)
val k = parts(1).toInt
val d = parts(2).toInt

val m = new scala.collection.mutable.HashMap[String, Int].withDefaultValue(0)

import scala.collection.mutable
def gen(s: String) = {
  def gen1(s: mutable.Set[String]): mutable.Set[String] = {
    var sl = mutable.HashSet.empty[String]
    for (i <- s) {
      for (l <- 0 until i.size) {
        val cs = i.toCharArray
        for (c <- List('A', 'C', 'G', 'T')) {
          cs(l) = c
          sl += String.copyValueOf(cs)
        }
      }
    }
    sl
  }
  var sur = mutable.Set(s)
  for (i <- 0 until d) {
    sur = gen1(sur)
  }
  sur
}
gnome.sliding(k).foreach(s => if (s.size == k) gen(s).foreach(i => m(i) = m(i) + 1))

val a = m.groupBy(_._2).toList.sortBy(_._1).last._2.keys.toList.sorted

println(a.mkString(" "))



