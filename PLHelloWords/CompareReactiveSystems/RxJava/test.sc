import $ivy.`io.reactivex.rxjava2:rxjava:2.1.9`
import io.reactivex._
import io.reactivex.subjects._
import io.reactivex.schedulers._
import io.reactivex.functions._



val a = BehaviorSubject.createDefault(3)


val f: BiFunction[_ >: Int, _ >: Int, _ <: Int] = new BiFunction[Int,Int, Int]() {

  def apply(a: Int, b: Int) = a + b
}
val b = Observable.combineLatest[Int, Int, Int](a, a, f)


val ff: Consumer[_ >: Int]  = new Consumer[Int]() {
	def accept(a: Int) = println(a.toString)
}

b.subscribe(ff)


a.onNext(4)

// no glitchs is handled
//:: 7
//:: 8
