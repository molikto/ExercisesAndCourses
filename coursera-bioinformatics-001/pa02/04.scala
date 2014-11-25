"Cyclopeptide Sequencing"

val input = scala.io.Source.fromFile("04.input").getLines()
val spec = input.next().split(" ").map(_.toInt).toList

val spec_toSet = spec.toSet

val table = scala.io.Source.fromFile("im.table").getLines().map(s => {val a = s.split(" "); (a(0)(0), a(1).toInt)}).toMap

var list = List("")

def mass(s: String) = s.foldLeft(0)((b, c) => b + table(c))

def sp(gnome: String) =  (1 until gnome.size).map(len => (gnome + gnome.substring(0,len - 1)).sliding(len).map(s => (s, mass(s)))).flatten.+:(("", 0)).+:((gnome, mass(gnome))).map(_._2).toList.sorted

def lsp(gnome: String) =  (1 until gnome.size).map(len => gnome.sliding(len).map(s => (s, mass(s)))).flatten.+:(("", 0)).+:((gnome, mass(gnome))).map(_._2).toList.sorted

def expand(l: List[String]) = l.flatMap(ll => table.keys.map(ll + _))


var res = List.empty[String]

while (!list.isEmpty) {
  list = expand(list).filter(ll => {
    if (sp(ll) == spec) {
      res = res.::(ll)
      false
    } else if (!lsp(ll).toSet.subsetOf(spec_toSet)) {
      false
    } else {
      true
    }

  })
}


println(res.map(_.map(table).mkString("-")).distinct.mkString(" "))