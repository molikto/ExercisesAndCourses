import scala.collection.mutable


val Water = '='
val Ground = '@'
val Fire = '#'
type Surface = Char

val W = 'W'
val S = 'S'
val A = 'A'
val D = 'D'
type Face = Char

def opp(f: Face): Face = f match {
  case W => S
  case S => W
  case A => D
  case D => A
}

def left(f: Face): Face = f match {
  case W => A
  case S => D
  case A => S
  case D => W
}

def right(f: Face): Face = f match {
  case W => D
  case S => A
  case A => W
  case D => S
}

val GoFront = 'F'
val GoBack = 'B'
val TurnLeft = 'L'
val TurnRight = 'R'
type Action = Char
val Actions = Seq(GoFront, GoBack, TurnLeft, TurnRight)

case class Point(x: Int, y: Int)
case class Sausage(p1: Point, p2: Point, p1t: Int= 0, p1b: Int = 0, p2t: Int = 0, p2b: Int = 0) {
  assert ((p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) || (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1) )
}

def translate(p: Point, face: Face, translate: Int) = {
  face match {
    case A => Point(p.x - translate, p.y)
    case S => Point(p.x, p.y + translate)
    case W => Point(p.x, p.y - translate)
    case D => Point(p.x + translate, p.y)
  }
}
case class Player(p: Point, face: Face)
case class Map(str: String) extends (Point => Surface) {
  val lines = str.split("\n").filter(!_.trim.isEmpty).map(a => a.toCharArray)
  assert(lines.forall(_.length == lines(0).length))
  val width = lines(0).length
  val height = lines.length
  def contains(p: Point) = p.x >= 0 && p.y >= 0 && p.x < width && p.y < height
  def apply(p: Point) = lines(p.y)(p.x)
}

case class State(sps: Seq[Sausage], player: Player) {
  def win(inital: Player) = player == inital && sps.forall(s => s.p1b == 1 && s.p2b == 1 && s.p1t == 1 && s.p2t == 1)
}
case class Level(name: String, map: Map, state: State)

def level(name: String, map: Map, sps: Seq[Sausage], player: Player): Level = {
  Level(name, map, State(sps.map(s => burn(map, s)), player))
}

def burn(map: Map, pushed: Sausage) = {
  val p1b = if (map(pushed.p1) == Fire) pushed.p1b + 1 else pushed.p1b
  val p2b = if (map(pushed.p2) == Fire) pushed.p2b + 1 else pushed.p2b
  pushed.copy(p1b = p1b, p2b = p2b)
}


val levels: Seq[Level] = Seq(
  level("Comely Hearth", Map("""
                               |=========
                               |===@@@===
                               |===@@@===
                               |=##@@@#==
                               |=##===##=
                               |=========
                             """.stripMargin), Seq(
    Sausage(Point(3, 2), Point(4, 2)),
    Sausage(Point(5, 2), Point(5, 3))
  ), Player(Point(4, 3), 'A')),
  level("Infant's Break", Map("""
                                |=========
                                |===@@@===
                                |===@@@===
                                |=##@@@#==
                                |=##===##=
                                |=========
                              """.stripMargin), Seq(
    Sausage(Point(3, 2), Point(4, 2)),
    Sausage(Point(5, 2), Point(5, 3))
  ), Player(Point(4, 3), 'A')),
  level("Southjaunt", Map("""
                            |======
                            |==@@==
                            |=@@@@=
                            |=@##@=
                            |=@##@=
                            |=@@@@=
                            |======
                          """.stripMargin), Seq(
    Sausage(Point(1, 3), Point(1, 4)),
    Sausage(Point(4, 3), Point(4, 4))
  ), Player(Point(2, 1), 'D')),
  level("Lachrymose Head", Map("""
                                 |=======
                                 |=@###@=
                                 |=@@@@@=
                                 |=@@@@@=
                                 |=@###@=
                                 |=======
                               """.stripMargin), Seq(
    Sausage(Point(1, 2), Point(2, 2)),
    Sausage(Point(4, 3), Point(5, 3)),
    Sausage(Point(3, 2), Point(3, 3))
  ), Player(Point(1, 4), 'W'))
)

type Route = Seq[Action]

// an action can be performed, or not
// return result state if can be performed
def action(map: Map, state: State, action: Action): Option[State] = {
  val player = state.player
  // returns None if the push failed: burned, pushed to water etc.
  // one thing to notice is the physics makes sure there is no angular change when passing the forces
  def push(sps: Seq[Sausage], p: Point, f: Face): Option[Seq[Sausage]] = {
    val dest = translate(p, f, 1)
    sps.find(s => s.p1 == dest || s.p2 == dest) match {
      case None => Some(sps)
      case Some(s) =>
        val tp1 = translate(s.p1, f, 1)
        val tp2 = translate(s.p2, f, 1)
        val (pushed, forcePoints): (Sausage, Seq[Point]) = {
          if ((p.x == s.p1.x && p.x == s.p2.x) || (p.y == s.p1.y && p.y == s.p2.y)) { // push on x
            (s.copy(p1 = tp1, p2 = tp2), Seq(translate(p, f, 2)))
          } else {
            (s.copy(p1 = tp1, p2 = tp2, p1t = s.p1b, p1b = s.p1t, p2t = s.p2b, p2b = s.p2t), Seq(s.p1, s.p2))
          }
        }
        // check is sausage fails
        if (!map.contains(pushed.p1) || !map.contains(pushed.p2) || map(pushed.p1) == Water && map(pushed.p2) == Water) {
          None
        } else {
          // burn and check over burn
          val burned = burn(map, pushed)
          if (burned.p1b > 1 || burned.p2b > 1) {
            None
          } else {
            val chainePushSps = forcePoints.foldLeft(Some(sps.filter(_ != s)) : Option[Seq[Sausage]]) { (sps, p) =>
              sps.flatMap(a => push(a, p, f))
            }
            chainePushSps.map(a => burned +: a)
          }
        }
    }
  }
  if (action == GoFront || action == GoBack) {
    val t = if (action == GoFront) 1 else -1
    val dest = translate(player.p, player.face, t)
    val destSurface = map(dest)
    if (destSurface == Water) {
      None
    } else {
      push(state.sps,
        if (action == GoFront) dest else player.p,
        if (action == GoFront) player.face else opp(player.face)
      ).map(sps => {
        state.copy(sps,
          Player(if (destSurface == Fire) player.p else dest,
            player.face)
        )
      })
    }
  } else {
    val turnTo = if (action == TurnLeft) left(player.face) else right(player.face)
    val front = translate(player.p, player.face, 1)
    push(state.sps, front, turnTo).flatMap(sps => {
      val pushPos = translate(front, turnTo, 1)
      push(sps, pushPos, opp(player.face))
    }).map(sps => {
      state.copy(sps, Player(player.p, turnTo))
    })
  }
}

case class WinException(route: Route) extends Exception("win")


def pp(route: Route, f: Face): Seq[Char] = {
  val keys = new mutable.ArrayBuffer[Char]()
  var face = f
  route.foreach {
    case GoFront =>
      keys.append(face)
    case GoBack =>
      keys.append(opp(face))
    case TurnLeft =>
      val l = left(face)
      keys.append(l)
      face = l
    case TurnRight =>
      val l = right(face)
      keys.append(l)
      face = l
  }
  keys.toList
}

def pp(map: Map, s: State) = {
  val buffer = new Array[Array[Char]](map.height)
  for (i <- 0 until map.height) {
    buffer(i) = new Array[Char](map.width)
  }
  for (h <- 0 until map.height) {
    for (w <- 0 until map.width) {
      buffer(h)(w) = map(Point(w, h))
    }
  }
  val p = s.player.p
  buffer(p.y)(p.x) = 'h'
  val pp = translate(p, s.player.face, 1)
  buffer(pp.y)(pp.x) = 'w'
  for (ss <- s.sps) {
    buffer(ss.p1.y)(ss.p1.x) = 'O'
    buffer(ss.p2.y)(ss.p2.x) = 'O'
  }
  println(buffer.map(a => a.mkString("")).mkString("\n"))
}

def solve(level: Level): Route = {
  val allStates = new mutable.HashMap[State, Route]

  allStates += (level.state -> List())


  var length = 0
  var previousStates = allStates
  try {
    while (true) {
      val newStates = previousStates.flatMap(s => {
        Actions.flatMap(a => {
          action(level.map, s._1, a).map(state => state -> (a +: s._2)).map(res => {
            if (res._1.win(level.state.player)) {
              throw new WinException(res._2)
            }
            res
          })
        })
      }).filter(a => !allStates.contains(a._1))
      allStates ++= newStates
      previousStates = newStates
      length += 1
      println(length)
    }
  } catch {
    case WinException(route) =>
      val rr = route.reverse
      //        rr.foldLeft(level.state) { (s, r) =>
      //          pp(level.map, s)
      //          action(level.map, s, r).get
      //        }
      //        println(rr)
      return pp(rr, level.state.player.face)
  }
  throw new Exception()
}

println(levels.take(1).map(a => a.name + ": " + solve(a).mkString(", ")).mkString("\n"))
