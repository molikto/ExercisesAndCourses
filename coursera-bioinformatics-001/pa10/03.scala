"BW Matching"



// I cheated to use pa03 as pa04 too...


val input = scala.io.Source.fromFile("03.input").getLines()

val lst = input.next().zipWithIndex.toVector

val fst = lst.sorted.toVector

val map = fst.zipWithIndex.map(a => (a._1._2, a._2)).sorted.map(_._2).toVector

val subs = input.next().split(" ").toList

val res = subs.map { sub =>
  var s = 0
  var e = lst.length - 1
  var i = sub.length - 1
  while (s <= e && i >= 0) {
    val c = sub(i)
    while (lst(s)._1 != c && s < e) s += 1
    while(lst(e)._1 != c && e >= s) e -= 1
    if (s <= e) {
      s = map(s)
      e = map(e)
    }
    i -= 1
  }
  e - s + 1
}

println(res.mkString(" "))
