/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import org.joda.time.Minutes

case class Path(path: List[Hop]) {

  val size: Int = path.size

  def orderByCost: Path = {
    Path(path.sortBy(h => h.cost))
  }

  def orderByTravelTime: Path = {
    Path(path.sorted(lengthOrdering))
  }
}

object lengthOrdering extends Ordering[Hop] {
  def compare(x: Hop, y: Hop): Int = {
    x.travelTime - y.travelTime
  }
}

case class Hop(from: Station, to: Station, train: Train) {
  require(train.stations.contains(from), s"$train train must pass the $from station!")
  require(train.stations.contains(to), s"$train train must pass the $to station!")

  val departureTime: Schedule = train.timeAt(from).get

  val arrivalTime: Schedule = train.timeAt(to).get

  val travelTime: Int = Minutes.minutesBetween(departureTime.time, arrivalTime.time).getMinutes

  val cost: Cost = {
    Minutes.minutesBetween(departureTime.time, arrivalTime.time).getMinutes match {
      case x: Int => Cost.generateCost(x, train.costModifier)
    }
  }

//  override def compare(that: Hop): Int = {
//    val thisTime = (arrivalTime, departureTime)
//  }
}
