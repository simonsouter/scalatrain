/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import org.joda.time.{DateTime, LocalTime}

import scala.io.Source

object TestData {

  val munich = Station("Munich")

  val nuremberg = Station("Nuremberg")

  val frankfurt = Station("Frankfurt")

  val cologne = Station("Cologne")

  val essen = Station("Essen")

  val reddish = Station("Reddish")

  import com.typesafe.training.scalatrain.Days._

  val ice724MunichTime = Schedule(Set(Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday), new LocalTime(8, 50), List(DateTime.parse("2015-05-13T07:22:05Z")))

  val ice724NurembergTime = Schedule(Set(Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday), new LocalTime(10, 0))

  val ice724FrankfurtTime = Schedule(Set(Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday), new LocalTime(12, 10))

  val ice724CologneTime = Schedule(Set(Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday), new LocalTime(13, 39))

  val ice726MunichTime = Schedule(Set(Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday), new LocalTime(7, 50))

  val ice726NurembergTime = Schedule(Set(Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday), new LocalTime(9, 0))

  val ice726FrankfurtTime = Schedule(Set(Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday), new LocalTime(11, 10))

  val ice726CologneTime = Schedule(Set(Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday), new LocalTime(13, 2))

  val ice724 = Train(
    InterCityExpress(724),
    Vector(
      ice724MunichTime -> munich,
      ice724NurembergTime -> nuremberg,
      ice724FrankfurtTime -> frankfurt,
      ice724CologneTime -> cologne
    ), DateTime.parse("2015-01-01T00:00:00Z")
  )

  val ice726 = Train(
    InterCityExpress(726),
    Vector(
      ice726MunichTime -> munich,
      ice726NurembergTime -> nuremberg,
      ice726FrankfurtTime -> frankfurt,
      ice726CologneTime -> essen
    ), DateTime.parse("2015-01-01T00:00:00Z")
  )

  val planner = new JourneyPlanner(Set(ice724, ice726))

  //Hops: ice724
  val ice724Munich2Nuremberg = Hop(munich, nuremberg, ice724)
  // 1 hour 10
  val ice724Nuremburg2Frankfurt = Hop(nuremberg, frankfurt, ice724)
  // 2 hour 10
  val ice724Frankfurt2Cologne = Hop(frankfurt, cologne, ice724) // 1 hour 29

  //Hops: ice726
  val ice726Munich2Nuremberg = Hop(munich, nuremberg, ice726)
  val ice726Nuremburg2Frankfurt = Hop(nuremberg, frankfurt, ice726)
  val ice726Frankfurt2Cologne = Hop(frankfurt, essen, ice726)

  //Paths
  val ice724Path = Path(List(ice724Munich2Nuremberg, ice724Nuremburg2Frankfurt, ice724Frankfurt2Cologne))
  val ice726Path = Path(List(ice726Munich2Nuremberg, ice726Nuremburg2Frankfurt, ice726Frankfurt2Cologne))
  val cheapPath = Path(List(ice724Munich2Nuremberg))
  val expensivePath = Path(ice724Path.path ++ ice726Path.path)

  val ice724PathByTime = Path(List(ice724Munich2Nuremberg, ice724Frankfurt2Cologne, ice724Nuremburg2Frankfurt))
  val ice726PathByCost = Path(List(ice726Munich2Nuremberg, ice726Frankfurt2Cologne, ice726Nuremburg2Frankfurt))

  //Useful statics
  val staticTime = DateTime.parse("2015-05-15T14:02:28.679+01:00")

  //Users
  val bob = User(Name("Bob", "Builder"), "bob@mail.net")
  val purchase1 = Purchase(bob, Station("Munich"), Station("Nuremberg"), staticTime, pType.Cheapest)

  //Purchases
  val purchases = Source.fromFile("./src/main/resources/test_purchases").getLines.toList.map(s => {
    val raw = s.split(",")
    Purchase(
      User(Name(raw(0), raw(1)), raw(2)),
      Station(raw(3)),
      Station(raw(4)),
      DateTime.parse(raw(5)),
      raw(6) match {
        case x if x == pType.Cheapest.toString => pType.Cheapest
        case x if x == pType.Fastest.toString => pType.Fastest
      }
    )
  })

}
