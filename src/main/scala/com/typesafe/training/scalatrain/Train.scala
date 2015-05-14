/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import scala.collection.immutable.Seq

case class Train(info: TrainInfo, schedule: Seq[(Time, Station)]) {
  require(schedule.size >= 2, "schedule must contain at least two elements")
  require(timesConsecutive(schedule.map(time => time._1)))

  val stations: Seq[Station] =
  // Could also be expressed in short notation: schedule map (_._2)
    schedule.map(stop => stop._2)

  val hops: Seq[Hop] =
    backToBackStations.map(b2b => Hop(b2b._1, b2b._2, this))

  def timeAt(station: Station): Option[Time] = {
    val maybeSchedule = schedule.find(_ match {
      case (_, `station`) => true
      case _ => false
    })

    maybeSchedule.map(_._1)
  }

  def backToBackStations: Seq[(Station, Station)] = {
    stations.zip(stations.tail)
  }

  def stationDepartures(): Seq[(Station, Time)] = {
    schedule.map(tuple => (tuple._2, tuple._1))
  }

  private def timesConsecutive(times: Seq[Time]): Boolean = {
    val timePairs = times.zip(times.tail)
    (for {
      (time1, time2) <- timePairs
    } yield {
        time1 < time2
      }).reduce(_ && _)
  }
}

case class Station(name: String)

sealed abstract class TrainInfo {
  def number: Int
}

case class InterCityExpress(override val number: Int, hasWifi: Boolean = false) extends TrainInfo

case class RegionalExpress(override val number: Int) extends TrainInfo

case class BavarianRegional(override val number: Int) extends TrainInfo


