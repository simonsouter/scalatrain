package com.typesafe.training.scalatrain

import org.joda.time.{DateTime, LocalTime}

object Days extends Enumeration {
  val Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday = Value
}

/**
 * Created by user on 14/05/15.
 */
case class Schedule(days: Set[Days.Value], time: LocalTime, exceptions: List[DateTime] = List()) {

  /**
   * Returns true if the schedule runs on the correct day and the time is not before the provided time.
   */
  def available(date: DateTime, requiredTime: LocalTime): Boolean = {
    if (days.contains(Days.apply(date.getDayOfWeek() - 1)) && !requiredTime.isAfter(time)) true else false
  }
}
