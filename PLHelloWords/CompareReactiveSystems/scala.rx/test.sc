
import $ivy.`com.lihaoyi::scalarx:0.3.2`
import rx._


val a = Var(1)

val b = Rx {
	a()
}

@

val a = Var(Seq.empty[Int])
val d = Var(1)
val b = Rx {
  if (a().size >= 0) {
    a().size
  } else {
    0
  }
}

val c = Rx{ a().size + b() + d() }

val c_debug = Rx{
  val v = c()
  println("c_debug: " + v)
  v
}

//:: c_debug: 1

val o = c_debug.trigger {
  println("o: " + c_debug.now)
}

//:: o: 1

a() = Seq(1)

// it handles some of the gliches
//:: c_debug: 3
//:: o: 3

d() = 4

//:: c_debug: 6
//:: o: 6


@


val a = Var(1)

val b = Rx {
	(Rx { a() }, Rx{ a() })
}

val c = Rx {
	b()._1() + b()._2()
}

val d = Rx {
	println("d triggered")
	c()
}

a() = 4

// this works


@


val useA = Var(true)
val a = Var(1)

val b = Var(2)

val c = useA.flatMap(use => {
	println("c triggered with " + use)
	if (use) a else b
})

//:: c triggered with true

val d = Rx {
	val cc = c()
	println("d with " + cc)
	c()
}

//:: d with 1

useA() = true

useA() = false

//:: c triggered with false
//:: d with 2

a() = 4

// nothing printed, so a is not part of the graph anymore



@

val a = Var(1)
val b = Var(2)
val aa = Rx {a()}
val bb = Rx {b()}
val c = Rx { println("c triggered "+ ( aa() + bb()))}
//:: c triggered 3
aa.kill()
b() = 3
//:: c triggered 4


/// kill doesn't really change graph structure, it just blocks new values from propagate down


@


val a = Var(1)
val c = Rx { 1/ a()}
val d = c.trigger {
	println("c tiggered")
	c.now
}
