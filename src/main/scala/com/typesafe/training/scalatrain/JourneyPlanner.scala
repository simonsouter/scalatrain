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

  val mapHopsByStations: Map[Station, Set[Hop]] = {
    val allHops = trains.flatMap(train => train.hops)
    stations.map(station => station -> allHops.filter(hop => hop.from == station)).toMap
  }

  def trainsAt(station: Station): Set[Train] =
  // Could also be expressed in short notation: trains filter (_.stations contains station)
    trains.filter(train => train.stations contains station)

//  def stopsAt(station: Station): Set[(Time, Train)] =
//    for {
//      train <- trains
//      time <- train.timeAt(station)
//    } yield (time, train)

  def calculateConnections(date: DateTime, departureTime: LocalTime, fromStation: Station, toStation: Station): List[List[Hop]] = {

    def traverse(fromStation: Station, acc:List[Hop], departureTime: LocalTime): List[List[Hop]] = {
      val hopsForStationAfterDepartureTimeOnDay = getHopsForStationFromTimeOnDay(fromStation, date, departureTime).
        filter(hop => !acc.contains(hop))

      hopsForStationAfterDepartureTimeOnDay match {
        case List() => List()
        case _ => {
          hopsForStationAfterDepartureTimeOnDay.flatMap(hop => {
            if(hop.to == toStation) {
              List(acc :+ hop)
            } else {
              traverse(hop.to, acc :+ hop, hop.arrivalTime.time)
            }
          })
        }
      }
    }
    traverse(fromStation, List(), departureTime)
  }

  /**
   * Gets all possible hops from the supplied fromStations, on the specified day AFTER the specified time.
   */
  def getHopsForStationFromTimeOnDay(fromStation: Station, date: DateTime, departureTime: LocalTime): List[Hop] = {
    mapHopsByStations.get(fromStation).getOrElse(Set()).filter(hop =>
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
}
