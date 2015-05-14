package com.typesafe.training.scalatrain

import org.joda.time.LocalTime

object Days extends Enumeration {
  val Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday = Value
}

/**
 * Created by user on 14/05/15.
 */
case class Schedule(days: Set[Days.Value], time: LocalTime) {

  /**
   * Returns true if the schedule runs on the correct day and the time is not before the provided time.
   */
  def available(day: Days.Value, requiredTime: LocalTime): Boolean = {
    if (days.contains(day) && !requiredTime.isAfter(time)) true else false
  }
}
