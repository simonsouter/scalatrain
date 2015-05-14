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

    //Is the date found in the exception?
    val exMatched = exceptions.find(ex => datesMatch(date, ex)).isDefined

    if (exMatched) false
    else if (days.contains(Days.apply(date.getDayOfWeek() - 1)) && !requiredTime.isAfter(time)) true
    else false
  }

  private def datesMatch(thisDate: DateTime, thatDate: DateTime): Boolean = {
    if (thisDate.getYear == thatDate.getYear &&
      thisDate.getMonthOfYear == thatDate.getMonthOfYear &&
      thisDate.getDayOfMonth() == thatDate.getDayOfMonth()) {
      true
    } else {
      false
    }
  }
}
