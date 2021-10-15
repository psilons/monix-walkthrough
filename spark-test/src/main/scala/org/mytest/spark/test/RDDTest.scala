package org.mytest.spark.test

import org.apache.spark.sql.SparkSession

object RDDTest extends App
{
    val session: SparkSession = SparkSession.builder()
      .master("local[1]") // no parallel for now
      .appName("MySparkTest")
      .getOrCreate()

    // we have to use configuration file, this place is too late.
    //session.sparkContext.setLogLevel("ERROR")
    val lines = session.sparkContext.textFile("README.md")
    println(lines.count()) // action
    lines.foreach(println) // print the content in the right order

    val lengths = lines.map(s => s.length)
    println(lengths.collect().mkString("Array(", ", ", ")"))

    println(lines.flatMap(line => line.split(" ")).filter(_.length > 5).collect().mkString("Array(", ", ", ")"))

    session.close() // cleanup

    val session1: SparkSession = SparkSession.builder()
      .master("local[3]") // parallel
      .appName("MySparkTest")
      .getOrCreate()

    // we have to use configuration file, this place is too late.
    //session.sparkContext.setLogLevel("ERROR")
    val rdd1 = session1.sparkContext.textFile("README.md")
    println("-" * 80)
    rdd1.foreach(println) // print the content not in the right order
    println("-" * 80)
    rdd1.take(50).foreach(println) // print the content in the right order

    session1.close() // cleanup
}
