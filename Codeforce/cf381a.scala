
object Main {

  def main(args: Array[String]) = {
    val input = scala.io.Source.stdin.getLines()
    val e, d = input.next().split(" ").map(_.toInt)
    import java.util.Calendar
    val a = Calendar.getInstance()
    a.set(0, 0, 0, e(0), e(1))
    a.get
  }
}
