/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */
package com.typesafe.training.scalatrain

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

  def stopsAt(station: Station): Set[(Time, Train)] =
    for {
      train <- trains
      time <- train.timeAt(station)
    } yield (time, train)

  def calculateConnections(departureTime: Time, fromStation: Station, toStation: Station): List[Path] = {

    def traverse(fromStation: Station, acc:List[Hop], departureTime: Time): List[Path] = {
      val hopsForStationAfterDepartureTime = getHopsForStationFromTime(fromStation, departureTime).
        filter(hop => !acc.contains(hop))

      hopsForStationAfterDepartureTime match {
        case List() => List()
        case _ => {
          hopsForStationAfterDepartureTime.flatMap(hop => {
            if(hop.to == toStation) {
              List(Path(acc :+ hop))
            } else {
              traverse(hop.to, acc :+ hop, hop.arrivalTime)
            }
          })
        }
      }
    }
    traverse(fromStation, List(), departureTime)
  }

  def getHopsForStationFromTime(fromStation: Station, departureTime: Time): List[Hop] = {
    mapHopsByStations.get(fromStation).getOrElse(Set()).filter(_.departureTime >= departureTime).toList
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
