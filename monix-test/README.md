# Monix Walkthrough

## Objectives
To find a tool similar to JDK concurrency, which can run tasks in parallel with
multi-threading in one JVM.
- Given a list of tasks, run them, and collect results back, as fast as possible.
- These tasks are business logic, and shouldn't mix with the multi-threading
  code. We may run in one thread, multi-thread, or in distributed clusters.

There maybe many ways to do this, we want to figure out the best way.
- minimal coding: dispatch, run, collect.
- cancellable, to prevent hanging.
- timeout, to restrict maximum run time.
- number of threads can be used.
- bring back errors if any

## Set up
Though monix can go up to Scala3, we still use 2.12 for testing since Spark is 
at most 2.12 and we want to use them together.

We use Scala 3, so go to https://mvnrepository.com/artifact/org.scala-lang/scala3-library
to find the latest version.

Go to https://monix.io/ to find latest version.

Add these 2 versions to the build.sbt. Refresh sbt view and make sure the libs
are there.

## Tutorials

A high level introduction: https://www.baeldung.com/scala/monix

https://scalac.io/blog/monix-why-you-should-care-about/

https://blog.softwaremill.com/scalaz-8-io-vs-akka-typed-actors-vs-monix-part-1-5672657169e1

https://monix.io/docs/current/tutorials/parallelism.html
