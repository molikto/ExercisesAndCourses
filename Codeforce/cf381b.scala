object Main {

  def main(args: Array[String]) = {
    val input = scala.io.Source.stdin.getLines()
    val n = input.next().toInt
    val ns = input.next().split(" ").map(_.toInt).toList
    var s = ns.sortBy(-_)
    s = s.head :: s
    var l = List(s(0))
    var r = List(s(1))
    s = s.drop(2)
    while(s.nonEmpty) {
      if (s.head != l.head) {
        l = s.head :: l
      } else if (s.head != r.head) {
        r = s.head :: r
      }
      s = s.tail
    }
    val res = l ++ r.reverse.tail
    println(res.length)
    println(res.mkString(" "))
  }
}