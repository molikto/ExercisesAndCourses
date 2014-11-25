/**
 *
 * input part
 */
val board = scala.io.Source.fromFile("sudoku_hard2.txt").getLines().map(_.map(c => if (c == '.') -1 else c - '1')).toList

import solver._

// vars i j k means....
val vars =
  (for (i <- 0 until 9) yield
    (for (j <- 0 until 9) yield
      (for (n <- 0 until 9) yield
        Variable(i.toString + j.toString + n.toString)).toIndexedSeq
      ).toIndexedSeq
    ).toIndexedSeq
def v(i: (Int, Int, Int)) = vars(i._1)(i._2)(i._3)

val f = And {
  List(
    // the start vars
    board.zipWithIndex.flatMap(a => a._1.zipWithIndex.withFilter(_._1 >= 0).map(p => (a._2, p._2, p._1))).map(v),
    // one number pre square
    for (i <- 0 until 9; j <- 0 until 9; k1 <- 0 until 9; k2 <- 0 until k1) yield Or(v(i, j, k1).!, v(i, j, k2).!),
    // the...
    for (i <- 0 until 9; k <- 0 until 9; j1 <- 0 until 9; j2 <- 0 until j1) yield Or(v(i, j1, k).!, v(i, j2, k).!),
    for (j <- 0 until 9; k <- 0 until 9; i1 <- 0 until 9; i2 <- 0 until i1) yield Or(v(i1, j, k).!, v(i2, j, k).!),
    for (is <- 0.until(9, 3); js <- 0.until(9, 3); i <- 0 until 9; j <- 0 until i; k <- 0 until 9) yield Or(v(is + i / 3, js + i % 3, k).!, v(is + j / 3, js + j % 3, k).!),
    for (i <- 0 until 9; k <- 0 until 9) yield Or((0 until 9).map(j => v(i, j, k)).toList),
    for (j <- 0 until 9; k <- 0 until 9) yield Or((0 until 9).map(i => v(i, j, k)).toList),
    for (is <- 0.until(9, 3); js <- 0 until(9, 3); k <- 0 until 9) yield Or((0 until 9).map(l => v(is + l / 3, js + l % 3, k)).toList)

  ).flatten
}



val rs = solve(f)

if (rs.size != 1) println("bad sudoku!")
else {
  val r = rs.head.withDefaultValue(True)
  for (i <- 0 until 9) {
    for (j <- 0 until 9)
      for (k <- 0 until 9)
        if (r(v(i, j, k)) == True)
          print(k + 1)
    print("\n")
  }
}



/**
 *
 * solver
 */

object solver {
  type Env = Map[Variable, Pure]

  val emptyEnv = Map.empty[Variable, Pure]

  class Pure(s: String) extends Formula {
    val vs = Set.empty[Variable] // this should not be called
    def normalize(env: Env): Formula = throw new IllegalStateException
    override def toString = s
  }
  object True extends Pure("true")
  object False extends Pure("false")

  abstract class Simple extends Formula

  object Variable {
    def apply(s: String) = new Variable(s)
  }

  object And {
    def apply(f: Formula*) = new And(f.toList)
    def apply(f: List[Formula]) = new And(f)
  }

  object Or {
    def apply(f: Formula*) = new Or(f.toList)
    def apply(f: List[Formula]) = new Or(f)
  }

  object Not {
    def apply(v: Variable) = new Not(v)
  }

  class Variable(val name: String) extends Simple {
    def normalize(env: Env) = if (!env.contains(this)) this else env(this)
    val vs = Set(this)
    override def equals(p1: scala.Any): Boolean = p1 match {
      case a: Variable => name.equals(a.name)
      case _ => false
    }
    override def toString = name.toString
    def ! = Not(this)
  }

  class Not(val f: Variable) extends Simple {
    val vs = f.vs
    def normalize(env: Env): Formula = f.normalize(env) match {
      case True => False
      case False => True
      case _ => this
    }
    override def toString = "!" + f.toString
  }

  class And(val f: List[Formula]) extends Formula {
    def normalize(env: Env) = {
      val nf = f.map(_.normalize(env)).filter(_ != True)
      if (nf.length == 0) True
      else if (nf.contains(False)) False
    //  else if (f.size == 1) f.head
      else new And(nf)
    }
    val vs = f.foldLeft(Set.empty[Variable])((a, b) => a ++ b.vs)
    override def toString = f.mkString("(", " & ", ")")
  }

  class Or(val f: List[Formula]) extends Formula {
    def normalize(env: Env) = {
      val nf = f.map(_.normalize(env)).filter(_ != False)
      if (nf.length == 0) False
      else if (nf.contains(True)) True
      else if (f.size == 1) f.head
      else new Or(nf)
    }
    val vs = f.foldLeft(Set.empty[Variable])((a, b) => a ++ b.vs)
    override def toString = f.mkString("(", " | ", ")")
  }

  abstract class Formula extends Comparable[Formula] {
    val vs: Set[Variable]
    def normalize(env: Env): Formula
    def compareTo(p1: Formula): Int = vs.size compareTo p1.vs.size
  }

  // return all pos
  def solve(cls: Formula): List[Env] = {
    cls match {
      case True =>
        println("WTF")
        List(emptyEnv)
      case False => List.empty[Env]
      case cn: And =>
        val sorted = cn.f.sorted
        val simples = sorted.takeWhile(p => p.isInstanceOf[Simple])
        if (simples.size > 0) {
          val m = simples.map({
            case v: Variable => (v, True)
            case n: Not => (n.f, False)
          }).toMap
          solve(cn.normalize(m)).map(_ ++ m)
        } else {
          val g = sorted.head.vs.head
          solve(cn.normalize(Map(g -> True))).map(_.updated(g, True)) ++ solve(cn.normalize(Map(g -> False))).map(_.updated(g, False))
        }
      case _ => throw new IllegalStateException
    }
  }

  /**
   *
   * solver tests
   */

/*   val a = Variable("a")
   val b = Variable("b")
   val c = Variable("c")

   println(solve(And(Or(a, b.!), Or(a, b))))

   println(solve(And(a, a.!)))

   println(solve(And(a, b, Or(b.!, c))))

  println(solve(And(Or(a.!, b.!), Or(b.!, c.!), Or(a.!, c.!), a)))*/
}
