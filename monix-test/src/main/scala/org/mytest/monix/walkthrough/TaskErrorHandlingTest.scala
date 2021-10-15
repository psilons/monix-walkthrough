package org.mytest.monix.walkthrough

import monix.eval.Task
import monix.execution.{CancelableFuture, Scheduler}

import scala.concurrent.duration._

// https://monix.io/docs/current/eval/task.html
object TaskErrorHandlingTest extends App
{
    // This is needed in all ways below
    //implicit val scheduler: Scheduler = monix.execution.Scheduler.global
    implicit val scheduler1 = Scheduler.forkJoin(
      name="my-forkjoin",
      parallelism=4,
      maxThreads=128,
      daemonic=false
    )

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

    scheduler1.shutdown()
}
