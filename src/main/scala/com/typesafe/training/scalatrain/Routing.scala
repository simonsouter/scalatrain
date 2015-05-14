/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

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
    (x.arrivalTime.time compareTo x.departureTime.time) - (y.arrivalTime.time compareTo y.departureTime.time)
  }
}

case class Hop(from: Station, to: Station, train: Train) {
  require(train.stations.contains(from), s"$train train must pass the $from station!")
  require(train.stations.contains(to), s"$train train must pass the $to station!")

  val departureTime: Schedule = train.timeAt(from).get

  val arrivalTime: Schedule = train.timeAt(to).get

  val cost: Cost = {
    arrivalTime.time compareTo departureTime.time match {
      case x: Int => Cost.generateCost(x, train.costModifier)
      case _ => Cost.generateCost(1, 1.0)
    }
  }

//  override def compare(that: Hop): Int = {
//    val thisTime = (arrivalTime, departureTime)
//  }
}
