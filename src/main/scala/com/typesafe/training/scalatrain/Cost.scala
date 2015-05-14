/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import play.api.libs.json.{Json, JsValue}

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
   * @param minutes
   * @return Cost object
   */
  def generateCost(minutes: Int, modifier: Double): Cost = {
    minutes match {
      case x if x <= 5 => Cost(2, 50) * modifier
      case x if x <= 15 => Cost(3, 50) * modifier
      case x if x > 15 => Cost(9, 99) * modifier
      case default => Cost(1,0) * modifier
    }
  }
}

case class Cost(dollars: Int = 0, cents: Int = 0) {
  require(dollars >= 0, s"Negative \$$dollars isn't allowed!")
  require(cents  >= 0 && cents <= 99, s"Cents must be 0-99, $cents found!")

  //Useful operators
  def multiply(that: Double): Cost = {
    val cent2dollar = (cents * that) / 100
    Cost((dollars * that).toInt + cent2dollar.toInt, ((cents * that) % 100).toInt)
  }
  def *(that: Double): Cost = multiply(that)

}


