"Greedy Sorting"

val input = scala.io.Source.fromFile("01.input").getLines()

val ns = input.next().drop(1).dropRight(1).split(" ").map(_.dropWhile(_ == '+').toInt).toIndexedSeq


def greedySort(p: IndexedSeq[Int]) = {
  (0 until p.length).foldLeft(List(p)) { (h, k) =>
    var n = h
    def lst = n.last
    if (lst(k) != k + 1) {
      val ind = lst.indexWhere(i => math.abs(i) == k + 1) + 1
      n = n ++ List(lst.slice(0, k) ++ lst.slice(k, ind).map(-_).reverse ++ lst.slice(ind, lst.length))
    }
    if (lst(k) == -(k + 1)) {
      n = n ++ List(lst.updated(k, k + 1))
    }
    n
  }
}

val r = greedySort(ns)

println(r.drop(1).map(_.map(i => if (i > 0) "+" + i.toString else i.toString).mkString("(", " ", ")")).mkString("\n"))


