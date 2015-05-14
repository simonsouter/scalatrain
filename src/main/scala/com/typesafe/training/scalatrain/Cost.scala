/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import play.api.libs.json.JsValue

import scala.util.Try

object Cost {
  def fromJson(js: JsValue): Option[Time] = {
    (for {
      hour <- Try((js \ "hours").as[Int])
      min = Try((js \ "minutes").as[Int]).getOrElse(0)
    } yield {
        Time(hour, min)
      }).toOption
  }

  /**
   * Given length of a Hop, generate the cost
   * @param minutes Minutes from departure time to arrival time, as an Int
   * @param modifier Double that increases or decreases ticket cost
   * @return Cost object
   */
  def generateCost(minutes: Int, modifier: Double): Cost = {
    (minutes match {
      case x if x <= 60 => Cost(2, 50)
      case x if x <= 120 => Cost(3, 50)
      case x if x > 120 => Cost(9, 99)
      case default => Cost(1,0)
    }) * modifier
  }
}

case class Cost(dollars: Int = 0, cents: Int = 0) extends Ordered[Cost] {
  require(dollars >= 0, s"Negative \$$dollars isn't allowed!")
  require(cents  >= 0 && cents <= 99, s"Cents must be 0-99, $cents found!")

  val asCents = dollars * 100 + cents

  //Useful operators
  def multiply(that: Double): Cost = {
    val cent2dollar = (cents * that) / 100
    Cost((dollars * that).toInt + cent2dollar.toInt, ((cents * that) % 100).toInt)
  }
  def *(that: Double): Cost = multiply(that)

  def -(that: Cost): Int = this.asCents - that.asCents

  def compare(that: Cost): Int = this - that
}


