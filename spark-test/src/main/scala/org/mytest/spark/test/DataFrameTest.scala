package org.mytest.spark.test

import org.apache.spark.sql.SparkSession

// https://www.analyticsvidhya.com/blog/2017/01/scala/
object DataFrameTest extends App
{
    val session: SparkSession = SparkSession.builder()
      .master("local[1]") // no parallel for now
      .appName("MySparkTest")
      .getOrCreate()

    val df = session.sqlContext.read.
        option("header", "true"). // we have headers
        option("inferSchema", "true"). // deduct data types from data
        csv("dataset/Pokemon.csv")
    println(df.columns.mkString(" "))
    println(df.count())
    df.printSchema()
    df.show(5)

    df.select("Name", "HP").show(10) // slicing

    df.filter(df("Total") > 600).show(10) // filtering
    df.filter(df("Legendary")).show()

    df.groupBy("Type 1").count().show() // groupby

    // join

    // run sql
    df.createOrReplaceTempView("pokemons")
    session.sqlContext.sql("select Name, \"Type 1\", \"Type 2\", Total from pokemons").show(5)

    session.close()
}
