package org.mytest.monix.walkthrough

import monix.eval.Task
import monix.execution.Scheduler
import org.mytest.monix.walkthrough.SimpleFunc.countPrimes

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object SimpleTaskTest extends App {
    val task = Task[Int] {
        countPrimes(1000000)
    }

    // This is needed in all ways below
    // don't use this, daemon
    //implicit val scheduler: Scheduler = monix.execution.Scheduler.global

    implicit val scheduler1 = Scheduler.forkJoin(
      name="my-forkjoin",
      parallelism=4,
      maxThreads=128,
      daemonic=false
    )

    // 1st way to run
    val res = task.runToFuture
    println(res.value) // Some(Success(78498))

    // 2nd way, with error handling, it's a callback
    val cancelable = task.runAsync {
        case Right(value) => println(value) // 78498
        case Left(ex) => println(s"Exception: ${ex.getMessage}")
    }

    val tasks = (10000 until 11000).map(i => Task(countPrimes(i)))// 100 tasks
    // 3rd way - https://monix.io/docs/current/tutorials/parallelism.html
    val aggr = Task.parSequence(tasks).map(_.toList) // This is ordered
    aggr.foreach(println)
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
