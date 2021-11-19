package org.mytest.monix.walkthrough

import monix.eval.Task

import scala.concurrent.Await
import scala.concurrent.duration.Duration

// http://www.beyondthelines.net/programming/futures-vs-tasks/
object SyncVsAsyncTasks extends App
{
    runSync()
    runAsync()

    // since we have executeAsync at the end of Task
    def runAsync() =
    {
        val taskA = Task // Task is just like Runnable in Java
        {
            SimpleFunc.debug("Starting taskA")
            Thread.sleep(3000)
            SimpleFunc.debug("Finished taskA")
        }.executeAsync

        val taskB = Task
        {
            SimpleFunc.debug("Starting taskB")
            Thread.sleep(2000)
            SimpleFunc.debug("Finished taskB")
        }.executeAsync

        import monix.execution.Scheduler.Implicits.global

        SimpleFunc.debug("Starting Main")
        val futureA = taskA.runToFuture
        val futureB = taskB.runToFuture
        SimpleFunc.debug("Continuing Main")

        Await.result(futureA zip futureB, Duration.Inf)
    }

    // since we don't have executeAsync at the end of Task
    def runSync() =
    {
        val taskA = Task
        {
            SimpleFunc.debug("Starting taskA")
            Thread.sleep(3000)
            SimpleFunc.debug("Finished taskA")
        }

        val taskB = Task
        {
            SimpleFunc.debug("Starting taskB")
            Thread.sleep(2000)
            SimpleFunc.debug("Finished taskB")
        }

        import monix.execution.Scheduler.Implicits.global

        SimpleFunc.debug("Starting Main")
        val futureA = taskA.runToFuture
        val futureB = taskB.runToFuture
        SimpleFunc.debug("Continuing Main")

        Await.result(futureA zip futureB, Duration.Inf)
    }
}
