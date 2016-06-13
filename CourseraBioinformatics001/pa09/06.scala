"Shortest Non-Shared Substring Problem"

val input = scala.io.Source.fromFile("06.input").getLines()

val s1, s2 = input.next()


class Node(val s: String, val ss: Seq[String], var cls: Array[Node]) {
  override def toString: String = {
    s.toString + cls.mkString("(", " | ", ")")
  }
}
def tree(ss: Seq[String]) = {
  var front = List(new Node("", ss, Array.empty[Node])) // the cls field is put by childs
  val root = front
  while (front.nonEmpty) {
    front = front.flatMap { n =>
      val cls = n.ss.filter(_.nonEmpty).groupBy(_.head).map { g =>
        val nl = (0 until g._2.head.length).takeWhile(i => g._2.forall(_(i) == g._2.head(i))).last + 1
        new Node(g._2.head.substring(0, nl), g._2.map(_.drop(nl)), Array.empty[Node])
      }
      n.cls = cls.toArray
      cls
    }
  }
  root.head
}

def trdex(root: Node, s: String) = {
  var a = ""
  var ret = s
  var r = root
  def sim(s: String, z: String) = (0 until (s.length min z.length)).takeWhile(i => s(i) == z(i)).lastOption match {
    case None => 0
    case Some(a) => a + 1
  }
  while (r != null && r.cls.nonEmpty) {
    val nn = r.cls.map(n => (n, sim(n.s, ret))).maxBy(_._2)
    val m = nn._1
    val l = nn._2
    r = if (m.s.length == l) m else null
    a = a + m.s.substring(0, l)
    ret = ret.drop(l)
  }
  a
}

def sufs(s1: String) = (1 to s1.length).map(l => s1.slice(s1.length - l, s1.length)).toList

val t = tree(sufs(s2))

val a = sufs(s1).map(s => (s, trdex(t, s))).filter(p => {
  p._1 != p._2
}).minBy(_._2.length)

val b = a._1.substring(0, a._2.length + 1)
println(b)



