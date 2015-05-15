/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import org.joda.time.Minutes

case class Path(path: List[Hop]) {

  val size: Int = path.size

  val totalCost: Cost = path.foldLeft(Cost(0))((c, h) => c + h.cost)

  val totalDistance: Int = path.foldLeft(0)((c, h) => c + h.kilometers)

  val totalTime: Int = {
    if (path.head == path.last) {
      Minutes.minutesBetween(path.head.departureTime.time, path.head.arrivalTime.time).getMinutes
    } else {
      Minutes.minutesBetween(path.head.departureTime.time, path.last.arrivalTime.time).getMinutes
    }
  }
}

case class Hop(from: Station, to: Station, train: Train) {
  require(train.stations.contains(from), s"$train train must pass the $from station!")
  require(train.stations.contains(to), s"$train train must pass the $to station!")

  val departureTime: Schedule = train.timeAt(from).get

  val arrivalTime: Schedule = train.timeAt(to).get

  val travelTime: Int = Minutes.minutesBetween(departureTime.time, arrivalTime.time).getMinutes

  val kilometers = (travelTime / 60) * 80

  val cost: Cost = Cost.generateCost(travelTime, train.costModifier)

}

object PathCostOrdering extends Ordering[Path] {
  def compare(x: Path, y: Path): Int = {
    x.totalCost - y.totalCost
  }
}

object PathTimeOrdering extends Ordering[Path] {
  def compare(x: Path, y: Path): Int = {
    x.totalTime - y.totalTime
  }
}

object Routing {
  implicit class ListOps(list: List[Path]) {
    def sortPathCost = {
      list.sorted(PathCostOrdering)
    }

    def sortPathTime = {
      list.sorted(PathTimeOrdering)
    }
  }
}