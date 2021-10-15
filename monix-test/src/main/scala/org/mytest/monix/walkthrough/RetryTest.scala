package org.mytest.monix.walkthrough

import monix.catnap.CircuitBreaker
import monix.eval._
import scala.concurrent.duration._

object RetryTest extends App
{
    import monix.execution.Scheduler

    implicit val scheduler1 = Scheduler.forkJoin(
      name="my-forkjoin",
      parallelism=4,
      maxThreads=128,
      daemonic=false
    )

    // none is working, the doc is no way to follow.

          // https://stackoverflow.com/questions/45014599/limit-the-number-of-retries-when-using-monix-observables-onerrorrestartif
      // https://hirokifujino.medium.com/functional-retry-handling-with-cats-retry-in-scala-7d0a5fd35956

       // retry 3 times on failure
    // https://monix.io/docs/current/eval/task.html#restart-on-error
    // not working at all!
    val error = Task[Int] { throw new Exception("Here failed!") }
    val restart = error.onErrorRestart(5).runAsync (r => println(r))


//    { result =>
//      result match {
//        case Right(value) => println(value) // 78498
//        case Left(ex) =>
//            println(s"Exception: ${ex.getMessage}")
//            Task.raiseError(ex)
//      }
//    }
    Thread.sleep(5000)

    import monix.catnap.CircuitBreaker
    import monix.eval._
    import scala.concurrent.duration._

    val circuitBreaker: Task[CircuitBreaker[Task]] = CircuitBreaker[Task].of(
        maxFailures = 5,
        resetTimeout = 1.seconds,

        onRejected = Task {
            println("Task rejected in Open or HalfOpen")
          },
          onClosed = Task {
            println("Switched to Close, accepting tasks again")
          },
          onHalfOpen = Task {
            println("Switched to HalfOpen, accepted one task for testing")
          },
          onOpen = Task {
            println("Switched to Open, all incoming tasks rejected for the next 10 seconds")
          }
        )

    val error11 = Task[Int] { throw new Exception("Here failed 2!") }
    val t11 = for {
          ci <- circuitBreaker
          r  <- ci.protect(error11)
        } yield r

//    t11.onErrorRestartLoop(0) { (e, times, retry) =>
//          // Retrying for a maximum of 10 times
//          if (times < 10)
//            circuitBreaker..flatMap(_ => retry(times + 1))
//          else
//            Task.raiseError(e)
//        }

//    val res11 = t11.runToFuture
//    println(res11.value)
//    res11.foreach(println)
    val res11 = t11.runAsync {
        case Right(value) => println(value) // 78498
        case Left(ex) => println(s"Exception: ${ex.getMessage}")
    } // tried only once, not 3 times.

    Thread.sleep(5000)
//    val error1 = Task[Either[Exception, Int]] { Left(new Exception("Now I am lost")) }
//    val restart1 = error.onErrorRestart(20).runAsync (r => println(r))
//
//    val error2 = Task[Int] { throw new Exception("No, not here!") }
//    Task.defer(error2).onErrorRestart(5).runAsync (r => println(r))
////
////    Thread.sleep(5000)
//
//    def retryOnFailure[A](source: Task[A], times: Int): Task[A] =
//      source.onErrorHandleWith { err =>
//        // No more retries left? Re-throw error:
//        if (times <= 0) Task.raiseError(err) else {
//          // Recursive call, yes we can!
//          retryOnFailure(source, times - 1)
//            // Adding 500 ms delay for good measure
//            .delayExecution(500.millis)
//        }
//      }

      //val t1 = retryOnFailure(error2, 3)

}
