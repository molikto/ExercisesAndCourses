"Convolution Cyclopeptide Sequencing"

val input = scala.io.Source.fromFile("07.input").getLines()
val m = input.next().toInt
val n = input.next().toInt
val spec = input.next().split(" ").map(_.toInt).toList


// this should be better with takewhile etc!!!
implicit class RichList[T](b: List[T]) {
  def takeWithTie(score: T => Int, n: Int) = {
    if (!b.isEmpty) {
      var last = score(b(0))
      var i = 0
      while ((i < b.size) && (i < n || last == score(b(i)))) {
        if (i < n)
          last = score(b(i))
        i += 1
      }
      b.slice(0, i)
    } else b
  }
}

def conv(spec: List[Int]) = (for (a <- spec; b<- spec) yield (a - b).abs).filter(_ != 0).sorted
  .zipWithIndex.filter(_._2 % 2 == 0).unzip._1
  .groupBy(c => c).map(a => (a._1, a._2.size)).toList


val spec_toSet = spec.toList

val pmass = spec.max

val table = conv(spec).filter(c => c._1 >= 57 && c._1 <= 200).sortBy(-_._2).takeWithTie(-_._2, m).unzip._1


println(table)

var list = List("")

def mass(s: Iterable[Int]) = s.foldLeft(0)(_ + _)

def sp(gnome: List[Int]) =  (1 until gnome.size).map(len => (gnome ++ gnome.slice(0,len - 1)).sliding(len).map(s => mass(s))).flatten.++(List(0, mass(gnome)))

def expand(l: List[List[Int]]) = l.flatMap(ll => table.map(ll.+:(_)))



def dp[T](init: List[T], trans: List[T] => List[T], stop: List[T] => Boolean,
          score: T => Int, pick: List[T] => Option[T], n: Int) = {
  var b = init
  var leader: Option[T] = None
  while (!stop(b)) {
    b = trans(b)
    var tb = b.map(i => (i, score(i))) // MUST PRECALCULATE THE SCORE!!!!
    tb = tb.sortBy(- _._2)
    b = tb.map(_._1)
    val ol = pick(b)
    leader = leader match {
      case None => ol
      case Some(l) => ol match {
        case Some(o) => if (score(o) > score(l)) ol else leader
        case None => leader
      }
    }
    if (!b.isEmpty) {
      var last = score(b(0))
      var i = 0
      while ((i < b.size) && (i < n || last == score(b(i)))) {
        if (i < n)
          last = score(b(i))
        i += 1
      }
      b = b.slice(0, i)
    }
  }
  leader
}


val a = dp[List[Int]](
  table.map(List(_)),
  l => expand(l).filter(mass(_) <= pmass),
  l => l.isEmpty,
  t => sp(t).intersect(spec).size,
  l => l.find(mass(_) == pmass),
  n
)


println(a.getOrElse(List.empty).map(_.toString).mkString("-"))







