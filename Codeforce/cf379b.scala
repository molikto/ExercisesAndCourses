
object Main {

  def main(args: Array[String]) = {
    val input = scala.io.Source.stdin.getLines()

    val n = input.next().toInt
    val a = input.next().split(" ").map(_.toInt).toList.toArray

    val sum = a.sum

    var cu = 0
    var p = 0

    var l = List.empty[Char]
    while (cu < sum) {
      if (a(p) == 0) {
        l = 'R' :: l
        p += 1
      } else if (p == a.length - 1) {
        l = 'R' :: 'L' :: 'P' :: l
        a(p) -= 1
        cu += 1
      } else if (p+1 < a.size && a(p+1) > 0 && a(p) > 1) {
        l = 'L' :: 'P' :: 'R' :: 'P' :: l
        cu += 2
        a(p) -= 1
        a(p+1) -= 1
      } else if (a(p) > 1) {
        l = 'L' :: 'R' :: 'P' :: l
        a(p) -= 1
        cu += 1
      } else {
        l = 'R' :: 'P' :: l
        a(p) -= 1
        cu += 1
        p += 1
      }
    }
    while (l.head != 'P') l = l.tail
    print(l.reverse.mkString)
  }
}