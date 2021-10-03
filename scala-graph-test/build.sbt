organization := "org.mytest.scalagraph.test"

name := "scalagraph-test"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.15"

scalacOptions ++= Seq("-deprecation", "-feature")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.6"

libraryDependencies += "org.scala-graph" %% "graph-core" % "1.13.1"
libraryDependencies += "org.scala-graph" %% "graph-constrained" % "1.13.0"
libraryDependencies += "org.scala-graph" %% "graph-dot" % "1.13.0"
libraryDependencies += "org.scala-graph" %% "graph-json" % "1.13.0"