package com.typesafe.training.scalatrain

import org.joda.time.LocalTime

object Days extends Enumeration {
  val Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday = Value
}

/**
 * Created by user on 14/05/15.
 */
case class Schedule(days: Set[Int], time: LocalTime) {


}
