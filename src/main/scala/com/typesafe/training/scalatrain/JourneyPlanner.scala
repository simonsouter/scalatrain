/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */
package com.typesafe.training.scalatrain


import org.joda.time.{DateTime, LocalTime}

import scala.collection.immutable.Set

class JourneyPlanner(trains: Set[Train]) {

  val stations: Set[Station] =
  // Could also be expressed in short notation: trains flatMap (_.stations)
    trains.flatMap(train => train.stations)

  lazy val sinkStations: Seq[Station] = {
    val sinkStations = for {
      (a,b) <- mapHopsByStations if b.isEmpty
    } yield a
    sinkStations.toSeq
  }

  val mapHopsByStations: Map[Station, Set[Hop]] = {
    val allHops = trains.flatMap(train => train.hops)
    stations.map(station => station -> allHops.filter(hop => hop.from == station)).toMap
  }

  def trainsAt(station: Station): Set[Train] =
    trains.filter(train => train.stations contains station)

  def calculateConnections(date: DateTime, fromStation: Station, toStation: Station): List[Path] = {
    calculateConnections(date, new LocalTime(0, 0), fromStation, toStation)
  }

  def calculateConnections(date: DateTime, departureTime: LocalTime, fromStation: Station, toStation: Station): List[Path] = {

    def traverse(fromStation: Station, acc: List[Hop], departureTime: LocalTime): List[Path] = {
      val hopsForStationAfterDepartureTimeOnDay = getHopsForStationFromTimeOnDay(fromStation, date, departureTime).
        filter(hop => !acc.contains(hop))

      hopsForStationAfterDepartureTimeOnDay match {
        case List() => List()
        case _ => {
          hopsForStationAfterDepartureTimeOnDay.flatMap(hop => {
            if (hop.to == toStation) {
              List(Path(acc :+ hop))
            } else {
              traverse(hop.to, acc :+ hop, hop.arrivalTime.time)
            }
          })
        }
      }
    }
    traverse(fromStation, List(), departureTime)
  }

  def calculateTrueCost(path: Path, date: DateTime): Cost = {
    date match {
      case x if DateTime.now plusDays 1 isAfter x => path.totalCost * 0.75
      case x if DateTime.now plusDays 14 isAfter x => path.totalCost * 1.5
      case x if DateTime.now plusDays 14 isBefore x => path.totalCost * 1.0
    }
  }

  /**
   * Gets all possible hops from the supplied fromStations, on the specified day AFTER the specified time.
   */
  def getHopsForStationFromTimeOnDay(fromStation: Station, date: DateTime, departureTime: LocalTime): List[Hop] = {
    mapHopsByStations.getOrElse(fromStation, Set()).filter(hop =>
      hop.departureTime.available(date, departureTime)
    ).toList
  }

  def isShortTrip(from: Station, to: Station): Boolean = {

    trains.exists(
      train => {
        train.stations.dropWhile(!_.equals(from)) match {
          case `from` +: `to` +: _ => true
          case `from` +: _ +: `to` +: _ => true
          case _ => false
        }
      }
    )
  }

  //  def stopsAt(station: Station): Set[(Time, Train)] =
  //    for {
  //      train <- trains
  //      time <- train.timeAt(station)
  //    } yield (time, train)
}
