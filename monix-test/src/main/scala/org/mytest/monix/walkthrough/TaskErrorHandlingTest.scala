package org.mytest.monix.walkthrough

import monix.eval.Task
import monix.execution.{CancelableFuture, Scheduler}

import scala.concurrent.duration._

// https://monix.io/docs/current/eval/task.html
object TaskErrorHandlingTest extends App
{
    // This is needed in all ways below
    implicit val scheduler: Scheduler = monix.execution.Scheduler.global

    // collect error - see 2nd way in SimpleTaskTest

    // cancel after that.
    val task = Task(1 + 1).delayExecution(10.seconds)
    val result: CancelableFuture[Int] = task.runToFuture
    // If we change our mind
    result.cancel()
    println(result.isCompleted)

    // timeout
    val task1 = Task(1+1).delayExecution(10.seconds).timeout(2.seconds)
    val res1 = task1.runAsync(r => println(r))
    //val res1 = task1.runToFuture.onComplete(r => println(r))
    Thread.sleep(3000)
    // now we should see the prints

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

    val error1 = Task[Either[Exception, Int]] { Left(new Exception("Now I am lost")) }
    val restart1 = error.onErrorRestart(20).runAsync (r => println(r))

    val error2 = Task[Int] { throw new Exception("No, not here!") }
    Task.defer(error2).onErrorRestart(5).runAsync (r => println(r))
//
//    Thread.sleep(5000)

    def retryOnFailure[A](source: Task[A], times: Int): Task[A] =
      source.onErrorHandleWith { err =>
        // No more retries left? Re-throw error:
        if (times <= 0) Task.raiseError(err) else {
          // Recursive call, yes we can!
          retryOnFailure(source, times - 1)
            // Adding 500 ms delay for good measure
            .delayExecution(500.millis)
        }
      }

      //val t1 = retryOnFailure(error2, 3)
      //https://stackoverflow.com/questions/45014599/limit-the-number-of-retries-when-using-monix-observables-onerrorrestartif
      // Looks like I am going to space before landing to a city 20 miles away.
}
