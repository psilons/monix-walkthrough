package org.mytest.scala.config.test

import com.typesafe.config.ConfigException.Missing
import com.typesafe.config.{ConfigFactory, ConfigValueFactory}
import com.typesafe.config.impl.SimpleConfigList

import scala.collection.JavaConverters._

object OverwriteArrayTest extends App
{
    val entries = ConfigFactory.parseString("""
      "quoted.key.1" = 5
      unquoted.key.2 = [1, 2, 3]
      array.run=[11, 12, 13]""")
    println(entries)
    // quoted is treated as a string, not tree path
    println(entries.getValue("\"quoted.key.1\""))
    try { entries.getValue("quoted.key.1") }
    catch {
        case e: Missing => println(e.getMessage)
    }

    println(entries.getValue("unquoted.key.2"))

    // now override array, fallback is to merge.
    val entries1 = entries.withValue("unquoted.key.2",
        ConfigValueFactory.fromIterable(List(4, 5, 6).asJava))//.withFallback(entries)

    println(entries1.getValue("unquoted.key.2"))
    println(entries.getValue("\"quoted.key.1\"")) // somehow it works with fallback

    val entries2 = entries.withValue("unquoted.key.2.0",
        ConfigValueFactory.fromAnyRef(10)).withFallback(entries)
    println(entries2.getValue("unquoted.key.2")) // override all, not just one element

    val entries3 = entries.withValue("array.run.1",
        ConfigValueFactory.fromAnyRef(10)).withFallback(entries)
    println(entries3.getValue("array.run")) // override all
}
