/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

case class Hop(from: Station, to: Station, train: Train) {
  require(train.stations.contains(from), s"$train train must pass the $from station!")
  require(train.stations.contains(to), s"$train train must pass the $to station!")

  val departureTime: Time = train.timeAt(from).get

  val arrivalTime: Time = train.timeAt(to).get

  val cost: Cost = {
    arrivalTime - departureTime match {
      case x: Int => Cost.generateCost(x, train.costModifier)
      case _ => Cost.generateCost(1, 1.0)
    }
  }

//  override def compare(that: Hop): Int = {
//    val thisTime = (arrivalTime, departureTime)
//  }
}
