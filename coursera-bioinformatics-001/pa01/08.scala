

val input = scala.io.Source.fromFile("08.input").getLines()
val gnome = input.next()
val parts = input.next().split(" ")
val k = parts(0).toInt
val d = parts(1).toInt

val m = new scala.collection.mutable.HashMap[String, Int].withDefaultValue(0)

def reverse(gnome: String) = {
  val rm = Map(('A', 'T'), ('T', 'A'), ('C', 'G'), ('G', 'C'))
  gnome.reverse.map(rm(_))
}
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
gnome.sliding(k).foreach(s => if (s.size == k) List(gen(s), gen(reverse(s))).flatten.foreach(i => m(i) = m(i) + 1))

val a = m.groupBy(_._2).toList.sortBy(_._1).last._2.keys.toList.sorted

println(a.mkString(" "))



