package simulations

import math.random
import scala.collection.parallel.mutable

class EpidemySimulator extends Simulator {

  def randomBelow(i: Int) = (random * i).toInt

  protected[simulations] object SimConfig {
    val population: Int = 300
    val roomRows: Int = 8
    val roomColumns: Int = 8

    val prevalence = 1
    val tmbt = 40
  }

  import SimConfig._

  val grid: IndexedSeq[IndexedSeq[scala.collection.mutable.Set[Person]]] = for (i <- 0 until roomRows) yield  for (j <- 0 until roomColumns) yield  new scala.collection.mutable.HashSet[Person]
  val persons: List[Person] = (for (i <- 0 until population) yield new Person(i)).toList

  class Person (val id: Int) {
    var infected = (id + 1) % 100 == 0
    var sick = false
    var immune = false
    var dead = false
    if (infected) iAmSick()

    if (!dead) move()
    var row: Int = randomBelow(roomRows)
    var col: Int = randomBelow(roomColumns)
    grid(row)(col) += this


    def moveByDir(dir: Int) = {
      var r, c = -1
      dir match {
        case 0 => {
          r = if (row == 0) roomRows -1 else row - 1
          c = col
        }
        case 1 => {
          r = row
          c = if (col == 0) roomColumns -1 else col - 1
        }
        case 2 => {
          r = if (row == roomRows - 1) 0 else row + 1
          c = col
        }
        case 3 => {
          r = row
          c = if (col == roomColumns - 1) 0 else col + 1
        }
      }
      (r, c, grid(r)(c))
    }
    def randomNab() = {
      moveByDir(randomBelow(4))
    }

    def iAmSick() = {
      infected = true
      afterDelay(6) {
        sick = true
      }
      afterDelay(14) {
        if (randomBelow(4) == 0) {
          dead = true
        } else {
          afterDelay(2) {
            sick = false
            immune = true
            afterDelay(2) {
              immune = false
              infected = false
            }
          }
        }

      }
    }

    def move(): Unit = if (!dead)
      afterDelay(randomBelow(4) + 1) {
        if (!dead) {
          val rs = (0 until 4).map(moveByDir).filter(_._3.forall((p: Person) => !p.sick && !p.dead))
          if (rs.length != 0) {
            val r = rs(randomBelow(rs.length))
            grid(row)(col) -= this
            row = r._1
            col = r._2
            grid(row)(col) += this
            val nr = r._3
            if (nr.exists((p: Person) => p.infected || p.immune)) {
              if (!infected && !immune) {
                if (randomBelow(100) < tmbt) {
                  iAmSick()
                }
              }
            }
          }
          move()
        }
      }
  }
}
