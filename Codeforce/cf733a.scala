
object Main {

  def main(args: Array[String]) = {
    val input = scala.io.Source.stdin.getLines()
    print(input.next().split(Array('A', 'E', 'I', 'O', 'U', 'Y')).map(l => l.length).reduceOption(_ max _).getOrElse(0) + 1)
  }
}
