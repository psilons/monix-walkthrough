package org.mytest.scal.circe.test

// https://stackoverflow.com/questions/62102137/decode-case-class-string-or-int-in-circe
// Depend on the JSON value, we map the value to different types
import io.circe.Decoder
import io.circe.parser._

sealed trait Value
object Value {
  final case class Values(id: Int, text: String) extends Value
  final case class IntValue(i: Int) extends Value
  final case class StringValue(s: String) extends Value

  implicit val valueDecoder: Decoder[Value] = Decoder[String]
    .map[Value](StringValue)
    .or(Decoder[Int].map[Value](IntValue))
    .or(Decoder.forProduct2("id", "text")(Values.apply).map[Value](identity))
}

final case class Example(field1: Int, value: Value)
object Example {
  implicit val exampDecoder: Decoder[Example] =
    Decoder.forProduct2("field1", "value")(Example.apply)
}

object MixedTypesExample {
  def main(args: Array[String]): Unit = {
    myTestRun()
  }

  def myTestRun(): Unit = {
    val fst =
      """
        |{
        |   "field1": 32,
        |   "value": {
        |      "id": 23,
        |      "text": "my text"
        |   }
        |}""".stripMargin

    val snd =
      """
        |{
        |   "field1": 32,
        |   "value": 21
        |}
        |""".stripMargin

    val third =
      """{
        |   "field1": 32,
        |   "value": "value"
        |}
        |""".stripMargin

    println(decode[Example](fst))
    println(decode[Example](snd))
    println(decode[Example](third))
  }

//  Right(Example(32,Values(23,text)))
//  Right(Example(32,IntValue(21)))
//  Right(Example(32,StringValue(value)))
}
