/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

case class Hop(from: Station, to: Station, train: Train) {
  require(train.stations.contains(from), s"$train train must pass the $from station!")
  require(train.stations.contains(to), s"$train train must pass the $to station!")

  def departureTime = {
    train.timeAt(from).get
  }

  def arrivalTime = {
    train.timeAt(to).get
  }

//  override def compare(that: Hop): Int = {
//    val thisTime = (arrivalTime, departureTime)
//  }
}
