/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import org.joda.time.Minutes

case class Path(path: List[Hop]) {

  val size: Int = path.size

  val totalCost: Cost = path.foldLeft(Cost(0))((c, h) => c + h.cost)

  val totalTime: Int = path.foldLeft(0)((t, h) => t + h.travelTime) // would be last arrival - first departure

  //  def orderByCost: Path = {
  //    Path(path.sortBy(h => h.cost))
  //  }
  //
  //  def orderByTravelTime: Path = {
  //    Path(path.sorted(lengthOrdering))
  //  }
}

object LengthOrdering extends Ordering[Hop] {
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

  val cost: Cost = Cost.generateCost(travelTime, train.costModifier)

  //  override def compare(that: Hop): Int = {
  //    val thisTime = (arrivalTime, departureTime)
  //  }

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
    def random = {
      List(4, 5, 6)
    }
    def sortPathCost = {
      list.sorted(PathCostOrdering)
    }

    def sortPathTime = {
      list.sorted(PathTimeOrdering)
    }
  }
}