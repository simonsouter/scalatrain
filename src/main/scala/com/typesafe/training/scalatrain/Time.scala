/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import play.api.libs.json.{Json, JsValue}

import scala.util.Try

object Time {
  def fromMinutes(minutes: Int): Time =
    Time(minutes / 60, minutes % 60)

  def fromJson(js: JsValue): Option[Time] = {
    (for {
      hour <- Try((js \ "hours").as[Int])
      min = Try((js \ "minutes").as[Int]).getOrElse(0)
    } yield {
        Time(hour, min)
      }).toOption
  }
}

case class Time(hours: Int = 0, minutes: Int = 0) extends Ordered[Time] {
  require(hours >= 0 && hours <= 23, "hours must be within 0 and 23")
  require(minutes >= 0 && minutes <= 59, "minutes must be within 0 and 59")

  val asMinutes: Int =
    hours * 60 + minutes

  def minus(that: Time): Int =
    this.asMinutes - that.asMinutes

  def -(that: Time): Int =
    minus(that)

  override lazy val toString: String = f"$hours%02d:$minutes%02d"

  override def compare(that: Time): Int = {
    this - that
  }

  def toJson(): JsValue = {
    Json.obj("hours" -> hours, "minutes" -> minutes)
  }
}
