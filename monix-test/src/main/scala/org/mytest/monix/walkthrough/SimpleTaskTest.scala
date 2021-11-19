package org.mytest.monix.walkthrough

import monix.eval.Task
import monix.execution.Scheduler
import monix.execution.schedulers.SchedulerService
import org.mytest.monix.walkthrough.SimpleFunc.countPrimes

import scala.concurrent.Await
import scala.concurrent.duration._

object SimpleTaskTest extends App {
    val task = Task[Int] {
        SimpleFunc.debug("Starting taskA")
        val res = countPrimes(1000000)
        SimpleFunc.debug("Finished taskA")
        res
    }

    // This is needed in all ways below
    // don't use this, daemon
    //implicit val scheduler: Scheduler = monix.execution.Scheduler.global

    implicit val scheduler1: SchedulerService = Scheduler.forkJoin(
      name="my-forkjoin",
      parallelism=4,
      maxThreads=128,
      daemonic=false
    )

    // 1st way to run, on main thread, not separate thread.
    val res = task.runToFuture
    println(res.value) // Some(Success(78498))

    val task1 = Task[Int] {
        SimpleFunc.debug("Starting taskA")
        val res = countPrimes(1000000)
        SimpleFunc.debug("Finished taskA")
        res
    }
    // 2nd way, with error handling, it's a callback
    val cancelable = task1.runAsync {
        case Right(value) => println(value)
        case Left(ex) => println(s"Exception: ${ex.getMessage}")
    } // this is not right, still on main thread
    // we see 78498 on main thread

    // callback
    val task2 = Task[Either[Throwable, Int]] {
        SimpleFunc.debug("Starting taskA")
        val res = countPrimes(1000000)
        SimpleFunc.debug("Finished taskA")
        Right(res)
        //Left(new Exception("Can you see it?")) // result or error
    }
    val t1 = task2.executeAsync.runToFuture
    t1.foreach(res => println("collect result: " +res))

    // Here is the official way, not pleasant since there is no error handling.
    val needToWait = task.delayExecution(2.seconds).executeAsync.runToFuture
    Await.result(needToWait, 3.seconds) // make it smaller to see timeout
    println("wait is done: " + needToWait.value) // this is on separate thread, but no error handling.

    val tasks = (10000 until 11000).map(i => Task(countPrimes(i)))// 1000 tasks
    // 3rd way - https://monix.io/docs/current/tutorials/parallelism.html
    val aggr = Task.parSequence(tasks).map(_.toList) // This is ordered
    Await.result(aggr.runToFuture, 5.seconds)
    aggr.foreach(println) // this is not working either, -- printed first
    println("-" * 80)

    // 4th way - if we don't care order(no need for map task -> result)
    val aggr1 = Task.parSequenceUnordered(tasks).map(_.toList)
    aggr1.foreach(println)
    println("-" * 80)

    // 5th way - windowed
    val batches = tasks.sliding(100, 100).map(batch => Task.parSequence(batch)).toIterable
    val aggr2 = Task.sequence(batches).map(_.flatten.toList)

    val fu = aggr2.runToFuture
    //Await.result(fu, Duration.Inf) // or Thread.sleep(2000) // have to sleep to see the result
    fu.foreach(println) // still not solid
    println("-" * 80)

    scheduler1.shutdown()
}
