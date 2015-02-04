/**
 *
 * User: molikto
 * Date: 01/12/15
 * Time: 00:26
 */

def profile(f: => Any): Unit = {
  val s = System.currentTimeMillis()
  println(f)
  println("time: " + (System.currentTimeMillis() - s))
}

def p001 = (0 until 1000).filter(i => (i % 3 == 0) || (i % 5 == 0)).sum

def fib(i: Int): Int = if (i < 2) 1 else if (i == 1) 2 else fib(i - 1) + fib(i - 2)

def p002 = Stream.from(0).map(fib).takeWhile(_ < 4e6).filter(_ % 2 == 0).sum


import scala.annotation.tailrec
import scala.collection.immutable.HashMap
import scala.math.BigInt._

val B0 = BigInt(0)

def sqrt(number : BigInt): BigInt = {
  def next(n : BigInt, i : BigInt) : BigInt = (n + i/n) >> 1

  val one = BigInt(1)

  var n = one
  var n1 = next(n, number)

  while ((n1 - n).abs > one) {
    n = n1
    n1 = next(n, number)
  }

  while (n1 * n1 > number) {
    n1 -= one
  }

  n1
}

object prime {
  def next(prev: Seq[BigInt]): Seq[BigInt] =
    if (prev.isEmpty)
      Vector(BigInt(2))
    else if (prev.size == 1)
      Vector(BigInt(2), BigInt(3))
    else
      prev :+ {
        var a = prev.last + 2
        while (!prev.forall(q => a % q != B0)) {
          a = a + 2
        }
        a
      }

  def next_(prev: Seq[Int]): Seq[Int] =
    if (prev.isEmpty)
      Vector(2)
    else if (prev.size == 1)
      Vector(2, 3)
    else
      prev :+ {
        var a = prev.last + 2
        while (!prev.forall(q => a % q != B0)) {
          a = a + 2
        }
        a
      }


  def until0(prev: Seq[Int],  i: Int): Seq[Int] = {
    var a = prev
    while (a.isEmpty || a.last < i) {
      a = next_(a)
    }
    a.dropRight(1)
  }

  def until0(i: BigInt): Seq[BigInt] = {
    var a = Seq.empty[BigInt]
    while (a.isEmpty || a.last < i) {
      a = next(a)
    }
    a.dropRight(1)
  }

  var until1_saved = Vector(2)
  def until1(i: Int) = {
    if (until1_saved.last >= i) {
      until1_saved.takeWhile(_ < i)
    } else {
      until1_saved = until1_(i + 100000)
      until1_saved.takeWhile(_ < i)
    }
  }

  def until1_(i: Int) = {
    if (i <= 2) Vector.empty[Int] else {
      val set = new Array[Boolean](i)
      val limit = Math.sqrt(i).toInt + 1
      set(2) = false
      var p = 2
      var k = 0
      while (p < limit) {
        k = p * 2
        while (k < i) {
          set(k) = true
          k += p
        }
        p += 1
        while (set(p)) {
          p += 1
        }
      }
      var l = Vector.empty[Int]
      var j = 2
      while (j < i) {
        if (!set(j)) {
          l = l :+ j
        }
        j += 1
      }
      l
    }
  }



  def until2(i: Int) = sieve0(Stream.range(2, i)).toList

  // this will fail due to recursion
  def sieve0(s: Stream[Int]): Stream[Int] = {
    if (s.isEmpty) {
      s
    } else {
      val n = s.head
      n +: sieve0(s.tail.filter(_ % n != 0))
    }
  }

  def is(p: BigInt): Boolean = until0(sqrt(p) + 1).forall(p % _ != B0)
  def is(p: Int): Boolean = {
    if (p % 2 == 0) false else if (p % 3 == 0) false else
    until1(Math.sqrt(p).toInt + 1).forall(p % _ != 0)
  }

  def n(i: Int) = {
    var a = Seq.empty[BigInt]
    for (j <- 0 until i) {
      a = next(a)
    }
    a.last
  }
}


def p003 = {
  val n = BigInt("600851475143")
  // OO
  // prime.until(n / 2 + 1).filter(p => n % p == B0).last
  if (prime.is(n)) n else
    Stream.iterate(1 + B0)(a => a + 1).takeWhile(a => a * a <= n).filter(a => n % a == B0 && prime.is(a)).last
}


def p004 =
  (for (i <- 100 to 999; j <- 100 to 999) yield (i * j).toString).filter(s => s == s.reverse).map(_.toInt).sorted.last


def p005 =
  Stream.iterate(20 + B0)(a => a + 20).dropWhile(n => !(1 to 20).forall(n % _ == B0)).head

def p006 = (1 to 100).map(i => i * i * i).sum  - (1 to 100).map(i => i * i).sum

def p007 = prime.n(10001)

def p008 = {
  val s = "73167176531330624919225119674426574742355349194934\n96983520312774506326239578318016984801869478851843\n85861560789112949495459501737958331952853208805511\n12540698747158523863050715693290963295227443043557\n66896648950445244523161731856403098711121722383113\n62229893423380308135336276614282806444486645238749\n30358907296290491560440772390713810515859307960866\n70172427121883998797908792274921901699720888093776\n65727333001053367881220235421809751254540594752243\n52584907711670556013604839586446706324415722155397\n53697817977846174064955149290862569321978468622482\n83972241375657056057490261407972968652414535100474\n82166370484403199890008895243450658541227588666881\n16427171479924442928230863465674813919123162824586\n17866458359124566529476545682848912883142607690042\n24219022671055626321111109370544217506941658960408\n07198403850962455444362981230987879927244284909188\n84580156166097919133875499200524063689912560717606\n05886116467109405077541002256983155200055935729725\n71636269561882670428252483600823257530420752963450".filter(_.isDigit)
  s.sliding(13).map(s => s.map(_ - '0').foldLeft(1 + B0)((a, b) => a * b)).max
}

def p009 = (for (i <- 0 to 1000; j <- (i + 1) to 1000) yield (i, j, 1000 - i - j)).filter(p => p._1 < p._3 && p._2 < p._3).filter(p => p._1 * p._1 + p._2 * p._2 == p._3 * p._3).map(p => p._1 * p._2 * p._3).head

def p010 = {
  prime.until1(2000000).map(_ + B0).sum
}


def p011 = {
  val ss = """08 02 22 97 38 15 00 40 00 75 04 05 07 78 52 12 50 77 91 08
             |49 49 99 40 17 81 18 57 60 87 17 40 98 43 69 48 04 56 62 00
             |81 49 31 73 55 79 14 29 93 71 40 67 53 88 30 03 49 13 36 65
             |52 70 95 23 04 60 11 42 69 24 68 56 01 32 56 71 37 02 36 91
             |22 31 16 71 51 67 63 89 41 92 36 54 22 40 40 28 66 33 13 80
             |24 47 32 60 99 03 45 02 44 75 33 53 78 36 84 20 35 17 12 50
             |32 98 81 28 64 23 67 10 26 38 40 67 59 54 70 66 18 38 64 70
             |67 26 20 68 02 62 12 20 95 63 94 39 63 08 40 91 66 49 94 21
             |24 55 58 05 66 73 99 26 97 17 78 78 96 83 14 88 34 89 63 72
             |21 36 23 09 75 00 76 44 20 45 35 14 00 61 33 97 34 31 33 95
             |78 17 53 28 22 75 31 67 15 94 03 80 04 62 16 14 09 53 56 92
             |16 39 05 42 96 35 31 47 55 58 88 24 00 17 54 24 36 29 85 57
             |86 56 00 48 35 71 89 07 05 44 44 37 44 60 21 58 51 54 17 58
             |19 80 81 68 05 94 47 69 28 73 92 13 86 52 17 77 04 89 55 40
             |04 52 08 83 97 35 99 16 07 97 57 32 16 26 26 79 33 27 98 66
             |88 36 68 87 57 62 20 72 03 46 33 67 46 55 12 32 63 93 53 69
             |04 42 16 73 38 25 39 11 24 94 72 18 08 46 29 32 40 62 76 36
             |20 69 36 41 72 30 23 88 34 62 99 69 82 67 59 85 74 04 36 16
             |20 73 35 29 78 31 90 01 74 31 49 71 48 86 81 16 23 57 05 54
             |01 70 54 71 83 51 54 69 16 92 33 48 61 43 52 01 89 19 67 48""".stripMargin.split("\n").map(_.split(" ").map(_.toInt))
  def conditional(b: Boolean, f : => Int) = if (b) Some(f) else None
  (for (i <- 0 until 20; j <- 0 until 20)
      yield Seq(
        conditional(i + 4 <= 20, (0 until 4).map(k => ss(i + k)(j)).reduce(_ * _)),
        conditional(j + 4 <= 20, (0 until 4).map(k => ss(i)(j + k)).reduce(_ * _)),
        conditional(i + 4 <= 20 && j + 4 <= 20, (0 until 4).map(k => ss(i + k)(j + k)).reduce(_ * _)),
        conditional(i - 4 >= 0 && j + 4 <= 20, (0 until 4).map(k => ss(i - k)(j + k)).reduce(_ * _))
      ).flatten
  ).flatten.max
}


def factors(n: Int) = {
  val ps = prime.until1(n + 1)
  val cs = new Array[Int](ps.size)
  var k = n
  var cont = true
  var i = 0
  while (cont && i < cs.size) {
    if (k < ps(i)) {
      cont = false
    }
    while (k % ps(i) == 0) {
      cs(i) += 1
      k /= ps(i)
    }
    i += 1
  }
  cs.zipWithIndex.filter(_._1 > 0).map(p => (ps(p._2), p._1))
}

def factorSize(i: Int) = {
  val a = factors(i).map(_._2 + 1).fold(1)(_ * _)
  a
}


def p012 = {
  var i = 0
  var fs = 0
  val N = 500
  while (fs <= N) {
    i += 1
    if (i % 2 == 0) {
      fs = factorSize(i / 2) * factorSize(i + 1)
    } else {
      fs = factorSize(i) * factorSize((i + 1) / 2)
    }
  }
  i * (i + 1) / 2
}


def p013 = {
  val s =
    """37107287533902102798797998220837590246510135740250
      |46376937677490009712648124896970078050417018260538
      |74324986199524741059474233309513058123726617309629
      |91942213363574161572522430563301811072406154908250
      |23067588207539346171171980310421047513778063246676
      |89261670696623633820136378418383684178734361726757
      |28112879812849979408065481931592621691275889832738
      |44274228917432520321923589422876796487670272189318
      |47451445736001306439091167216856844588711603153276
      |70386486105843025439939619828917593665686757934951
      |62176457141856560629502157223196586755079324193331
      |64906352462741904929101432445813822663347944758178
      |92575867718337217661963751590579239728245598838407
      |58203565325359399008402633568948830189458628227828
      |80181199384826282014278194139940567587151170094390
      |35398664372827112653829987240784473053190104293586
      |86515506006295864861532075273371959191420517255829
      |71693888707715466499115593487603532921714970056938
      |54370070576826684624621495650076471787294438377604
      |53282654108756828443191190634694037855217779295145
      |36123272525000296071075082563815656710885258350721
      |45876576172410976447339110607218265236877223636045
      |17423706905851860660448207621209813287860733969412
      |81142660418086830619328460811191061556940512689692
      |51934325451728388641918047049293215058642563049483
      |62467221648435076201727918039944693004732956340691
      |15732444386908125794514089057706229429197107928209
      |55037687525678773091862540744969844508330393682126
      |18336384825330154686196124348767681297534375946515
      |80386287592878490201521685554828717201219257766954
      |78182833757993103614740356856449095527097864797581
      |16726320100436897842553539920931837441497806860984
      |48403098129077791799088218795327364475675590848030
      |87086987551392711854517078544161852424320693150332
      |59959406895756536782107074926966537676326235447210
      |69793950679652694742597709739166693763042633987085
      |41052684708299085211399427365734116182760315001271
      |65378607361501080857009149939512557028198746004375
      |35829035317434717326932123578154982629742552737307
      |94953759765105305946966067683156574377167401875275
      |88902802571733229619176668713819931811048770190271
      |25267680276078003013678680992525463401061632866526
      |36270218540497705585629946580636237993140746255962
      |24074486908231174977792365466257246923322810917141
      |91430288197103288597806669760892938638285025333403
      |34413065578016127815921815005561868836468420090470
      |23053081172816430487623791969842487255036638784583
      |11487696932154902810424020138335124462181441773470
      |63783299490636259666498587618221225225512486764533
      |67720186971698544312419572409913959008952310058822
      |95548255300263520781532296796249481641953868218774
      |76085327132285723110424803456124867697064507995236
      |37774242535411291684276865538926205024910326572967
      |23701913275725675285653248258265463092207058596522
      |29798860272258331913126375147341994889534765745501
      |18495701454879288984856827726077713721403798879715
      |38298203783031473527721580348144513491373226651381
      |34829543829199918180278916522431027392251122869539
      |40957953066405232632538044100059654939159879593635
      |29746152185502371307642255121183693803580388584903
      |41698116222072977186158236678424689157993532961922
      |62467957194401269043877107275048102390895523597457
      |23189706772547915061505504953922979530901129967519
      |86188088225875314529584099251203829009407770775672
      |11306739708304724483816533873502340845647058077308
      |82959174767140363198008187129011875491310547126581
      |97623331044818386269515456334926366572897563400500
      |42846280183517070527831839425882145521227251250327
      |55121603546981200581762165212827652751691296897789
      |32238195734329339946437501907836945765883352399886
      |75506164965184775180738168837861091527357929701337
      |62177842752192623401942399639168044983993173312731
      |32924185707147349566916674687634660915035914677504
      |99518671430235219628894890102423325116913619626622
      |73267460800591547471830798392868535206946944540724
      |76841822524674417161514036427982273348055556214818
      |97142617910342598647204516893989422179826088076852
      |87783646182799346313767754307809363333018982642090
      |10848802521674670883215120185883543223812876952786
      |71329612474782464538636993009049310363619763878039
      |62184073572399794223406235393808339651327408011116
      |66627891981488087797941876876144230030984490851411
      |60661826293682836764744779239180335110989069790714
      |85786944089552990653640447425576083659976645795096
      |66024396409905389607120198219976047599490197230297
      |64913982680032973156037120041377903785566085089252
      |16730939319872750275468906903707539413042652315011
      |94809377245048795150954100921645863754710598436791
      |78639167021187492431995700641917969777599028300699
      |15368713711936614952811305876380278410754449733078
      |40789923115535562561142322423255033685442488917353
      |44889911501440648020369068063960672322193204149535
      |41503128880339536053299340368006977710650566631954
      |81234880673210146739058568557934581403627822703280
      |82616570773948327592232845941706525094512325230608
      |22918802058777319719839450180888072429661980811197
      |77158542502016545090413245809786882778948721859617
      |72107838435069186155435662884062257473692284509516
      |20849603980134001723930671666823555245252804609722
      |53503534226472524250874054075591789781264330331690""".stripMargin
  s.split("\n").map(s => BigInt(s)).reduce(_ + _).toString.take(10)
}


var collatz_saved = Vector(-1, 1)

def collatz_(i: Long) = {
  def nn = (n: Long) =>  if (n % 2 == 0) n / 2 else n * 3 + 1
  val start = Stream.iterate(i)(nn).takeWhile(_ >= collatz_saved.size)
  start.size + collatz_saved(nn(start.last).toInt)
}

def collatz(i: Int) = {
  if (i == collatz_saved.size) {
    val c = collatz_(i)
    collatz_saved = collatz_saved :+ c
    c
  } else if (i > collatz_saved.size) {
    collatz_(i)
  } else {
    collatz_saved(i)
  }
}

def p014 = {
  (1 until 1000000).map(i => (i, collatz(i))).maxBy(_._2)._1
}

def t(i: Int) = (1 to i).map(_ + B0).fold(1 + B0)(_ * _)

def c(i: Int, j: Int) = t(i) / t(j) / t(i - j)

def p015 = c(40, 20)


def p016 = (2 + B0).pow(1000).toString.map(_ - '0').sum


def p017_p067 = {
  val s = "" // deleted
  val ns = s.split("\n").map(_.split(" ").map(_.toInt).toBuffer)
  for (l <- 0 until ns.size) {
    val c = ns.size - 2 - l
    for (i <- 0 to c) {
      ns(c)(i) +=  ns(c + 1)(i) max ns(c + 1)(i + 1)
    }
  }
  ns(0)(0)
}

def p020 = t(100).toString.map(_ - '0').sum

def psums(n: Int): Int = {
  if (n == 0) 0 else if (n == 1) 0 else
  Stream.iterate((Seq(1), factors(n).toList :+ (1, 1)))(ps => {
    val ms = ps._1
    val fs = ps._2
    val f = fs.head
    val res = (ms.flatMap(m => {
      (0 to f._2).map(a => Math.pow(f._1, a).toInt * m)
    }), fs.tail)
    res
  }).takeWhile(_._2.nonEmpty).lastOption.map(_._1.sum - n).getOrElse(0)
}

def p021 = {
  (2 until 10000).filter(i => psums(psums(i)) == i && psums(i) != i).sum
}

def p022 = {
  val N = 28123
  val abd = (0 to N).map(i => psums(i) > i)
  (1 to N).filter(n => !(0 to n).exists(k => abd(k) && abd(n - k))).sum
}

def p023 = {
  val start = Seq(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).toBuffer
  var top = 0
  var s = 0
  while (top < start.size && s < 1000000) {
    val ns = s + t(start.size - top - 1).toInt
    if (ns < 1000000) {
      val n = (top until start.size).find(n => start(n) > start(top)).get
      val t = start(top)
      start(top) = start(n)
      start(n) = t
      s = ns
    } else {
      top += 1
    }
  }
  start.mkString
}


def p024 = {
  var prev = B0 + 1
  var cur = B0 + 1
  var n = 2
  while (cur.toString.size < 1000) {
    val ocur = cur
    cur = prev + cur
    prev = ocur
    n += 1
  }
  n
}

case class Rational(top: BigInt, bottom: BigInt) {
  def decimal = {
    val beforePoint = top / bottom
    var rem = top % bottom
    var floatings = Seq.empty[(BigInt, BigInt)]
    while (rem != B0 && floatings.find(_._1 == rem).isEmpty) {
      val orem = rem
      val p = rem * 10 / bottom
      rem = rem * 10 % bottom
      floatings = floatings :+ (orem, p)
    }
    if (rem != B0) {
      val sIndex = floatings.zipWithIndex.find(_._1._1 == rem).get._2
      (beforePoint,  floatings.take(sIndex).map(_._2).mkString,  floatings.drop(sIndex).map(_._2).mkString)
    } else {
      (beforePoint, floatings.map(_._2).mkString, "")
    }
  }
}

def p026 = (1 until 1000).maxBy(a => Rational(1, a).decimal._3.length)


def p027 = {
  val a = (for (i <- -999 until 1000; j <- -999 until 1000; if prime.is(j)) yield (i, j)).maxBy(a => {
    Stream.from(0).takeWhile(k => prime.is(Math.abs(k * k + a._1 * k + a._2))).lastOption.getOrElse(0)
  })
  a._1 * a._2
}

def p028 = {
  val N = 1001
  val R = (N + 1) / 2
  1 + (2 to R).map(i => {
    val q = i * 2 - 1
    val end = q * q
    val res = end * 4 - (i - 1) * 12
    res
  }).sum
}

def p029 = {
  (for (i <- 2 to 100; j <- 2 to 100) yield i pow j).distinct.size
}

def p030 = {
  (2 to 413343).filter(i => i.toString.map(_ - '0').map(_ pow 4).sum == i).sum
}

def p031 = {
  val coins = Seq(2, 5, 10, 20, 50, 100, 200)
  var c = 0
  for (i200 <- 0 to (200 / 200)) {
    for (i100 <- 0 to (200 / 100)) {
      for (i50 <- 0 to (200 / 50)) {
        for (i20 <- 0 to (200 / 20)) {
          for (i10 <- 0 to (200 / 10)) {
            for (i5 <- 0 to (200 / 5)) {
              for (i2 <- 0 to (200 / 2)) {
                if (i2 * 2 + i5 * 5 + i10 * 10 + i20 * 20 + i50 * 50 + i100 * 100 + i200 * 200 <= 200) {
                  c += 1
                }
              }
            }
          }
        }
      }
    }
  }
  c
}


def p032 = {
  (1 to 9).toList.permutations.map(is => {
    (for (s <- 0 until 8; k <- (s + 1) until 8) yield {

        val a = is.slice(0, s + 1).mkString.toInt
        val b = is.slice(s + 1, k + 1).mkString.toInt
        val c = is.slice(k + 1, is.size).mkString.toInt
        if (a * b == c) {
          Some(c)
        } else None
    }).flatten
  }).flatten.toList.distinct.sum
}

def p034 = {
  (10 to 2903040).filter(i => i.toString.map(a => t(a - '0')).sum == i + B0).sum
}

def p035 = {
  val ps = prime.until1(1000000)
  ps.count(p => {
    val cs = p.toString
    (1 until cs.size).forall(i => ps.contains((cs.drop(i) + cs.take(i)).toInt))
  })
}

def p036 = {
  (0 until 1000000).filter(i => i.toString == i.toString.reverse && Integer.toBinaryString(i) == Integer.toBinaryString(i).reverse).sum
}

def p037 = {
  val ps = prime.until1(1000000)
  ps.filter(p => {
    val cs = p.toString
    cs.size > 1 && (1 until cs.size).map(i => cs.drop(i).toInt).forall(k => ps.contains(k)) && (1 until cs.size).map(i => cs.dropRight(i).toInt).forall(k => ps.contains(k))
  }).sum
}

def p038 = (2 to 9).map(i => 1 to i).map(ms => Stream.from(1).map(k => ms.map(a => (k * a).toString).mkString.toLong).takeWhile(_ <= 987654321).filter(_.toString.sorted == "123456789")).flatten.max


def p039 = (1 to 1000).maxBy(i => {
  (for (a <- 1 until i; b <- 1 until Math.min(a + 1, i - a)) yield a * a + b * b == (i - a - b) * (i - a - b)).count(a => a)
})

def p040 = {
  val a = new StringBuilder
  var i = 1
  while (a.size <= 1000000) {
    a.append(i)
    i += 1
  }
  (a(0) - '0') * (a(9) - '0') * (a(99) - '0') * (a(999) - '0') * (a(9999) - '0') * (a(99999) - '0') * (a(999999) - '0')
}

def p041 = {
  (4 to 9).map(i => (1 to i).mkString.permutations.map(_.toInt).filter(a => prime.is(a))).flatten.max
}


def p042 = {
  val p = Seq(2,3, 5, 7, 11, 13, 17)
  (0 to 9).permutations.filter(_(0) != '0').filter(n => (1 to 7).forall(i => n.slice(i, i + 3).mkString.toInt % p(i - 1) == 0)).map(_.mkString.toLong).sum
}

profile(p042)

