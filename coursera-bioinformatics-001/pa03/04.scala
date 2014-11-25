"Greedy Motif Search"

val input = scala.io.Source.fromFile("04.input").getLines()
val kt = input.next().split(" ").map(_.toInt)
val (k, t) = (kt(0), kt(1))
val dnas = input.toList

val table = Map('A'-> 0, 'C' -> 1, 'G' -> 2, 'T' -> 3)
val revt = "ACGT"

val mat = input.toList.map(_.split(" ").map(_.toFloat))



def patt(text: String, mat: Seq[Seq[Float]]) = text.sliding(k).maxBy(s => s.zipWithIndex.foldLeft(1f)((p, c) => p * mat(c._2)(table(c._1))))

def form(m: String): List[String] =
  (1 until t).foldLeft(List(m))((ms, i) => {
    val mat = (0 until ms(0).length).map(n => {
      val list = ms.map(_(n))
      revt.map(c => list.count(_  == c).toFloat / list.size)
    })
    val m = patt(dnas(i), mat)
    ms ++ List(m)
  })

def consensuc(ms: List[String]) = (0 until ms(0).length).map(n => ms.map(_(n)).groupBy(c => c).toList.sortBy(_._2.size).last._1)

def dis(a: Seq[Char], b: Seq[Char]) = a.zip(b).count(pr => pr._1 != pr._2)

def score(ms: List[String]): Float = {val c = consensuc(ms); ms.map(dis(_, c)).sum}

val r = dnas(0).sliding(k).map(form).minBy(score)

println(r.mkString("\n"))
