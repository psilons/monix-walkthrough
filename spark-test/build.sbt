organization := "org.mytest.spark.test"

name := "spark-test"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.15"

scalacOptions ++= Seq("-deprecation", "-feature")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.6"

val sparkVersion = "3.2.0"
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-mllib" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-graphx" % sparkVersion
