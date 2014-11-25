package suggestions
package observablex

import scala.concurrent.{Promise, Future, ExecutionContext}
import scala.util._
import scala.util.Success
import scala.util.Failure
import java.lang.Throwable
import rx.lang.scala.Observable
import rx.lang.scala.Scheduler
import rx.lang.scala.subscriptions.Subscription

object ObservableEx {

  /** Returns an observable stream of values produced by the given future.
   * If the future fails, the observable will fail as well.
   *
   * @param f future whose values end up in the resulting observable
   * @return an observable completed after producing the value of the future, or with an exception
   */
  def apply[T](f: Future[T])(implicit execContext: ExecutionContext): Observable[T] = Observable(observer => {

    var cancelled = false
    f onSuccess {
      case v => if (!cancelled) { observer.onNext(v); observer.onCompleted() }
    }
    f onFailure {
      case t => if (!cancelled) { observer.onError(t)}
    }
    Subscription {
      cancelled = true
    }
  })

}