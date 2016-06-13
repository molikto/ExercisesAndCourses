
object Main {

  def main(args: Array[String]) = {
    val input = scala.io.Source.stdin.getLines()
    val n = input.next().toInt
    var ns = input.next().split(" ").map(_.toInt).toList
    var s = 0
    var e = ns.length
    var l = List.empty[Int]
    while (ns.nonEmpty) {
      if (ns.head > ns.last) {
        l = ns.head :: l
        ns = ns.drop(1)
      } else {
        l = ns.last :: l
        ns = ns.dropRight(1)
      }
    }
    val as = l.reverse.grouped(2).map(a => if (a.size == 2) (a(0), a(1)) else (a(0), 0)).toList.unzip
    print(as._1.sum + " " + as._2.sum)
  }
}
