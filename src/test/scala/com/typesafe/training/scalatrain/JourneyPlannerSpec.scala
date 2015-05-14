/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import TestData._
import java.lang.{IllegalArgumentException => IAE}
import org.joda.time.{DateTime, LocalTime}
import org.scalatest.{Matchers, WordSpec}

class JourneyPlannerSpec extends WordSpec with Matchers {

  "stations" should {
    "be initialized correctly" in {
      planner.stations shouldEqual Set(munich, nuremberg, frankfurt, cologne, essen)
    }
  }

  "Calling trainsAt" should {
    "return the correct trains" in {
      planner.trainsAt(munich) shouldEqual Set(ice724, ice726)
      planner.trainsAt(cologne) shouldEqual Set(ice724)
    }
  }

//  "Calling stopsAt" should {
//    "return the correct stops" in {
//      planner.stopsAt(munich) shouldEqual Set(ice724MunichTime -> ice724, ice726MunichTime -> ice726)
//    }
//  }

  "Calling isShortTrip" should {
    "return false for more than one station in between" in {
      planner.isShortTrip(munich, cologne) shouldBe false
      planner.isShortTrip(munich, essen) shouldBe false
    }
    "return true for zero or one stations in between" in {
      planner.isShortTrip(munich, nuremberg) shouldBe true
      planner.isShortTrip(munich, frankfurt) shouldBe true
      planner.isShortTrip(nuremberg, frankfurt) shouldBe true
      planner.isShortTrip(nuremberg, essen) shouldBe true
    }
  }

  "System Map" should {
    "have the correct hops from Munich" in {
      planner.mapHopsByStations.get(munich).get shouldBe Set(ice724Munich2Nuremberg, ice726Munich2Nuremberg)
    }

    "have the correct hops from Nuremberg" in {
      planner.mapHopsByStations.get(nuremberg).get shouldBe Set(ice724Nuremburg2Frankfurt, ice726Nuremburg2Frankfurt)
    }

    "have no hops from Reddish" in {
      planner.mapHopsByStations.get(reddish) shouldBe None
    }
  }

  "Calculate Connections with time" should {
    "Munich to Frankfurt" in {
      val munichToFrankfurt = planner.calculateConnections(new DateTime(), new LocalTime(8, 30), munich, frankfurt)
      munichToFrankfurt.size shouldBe 1
      val route1 = munichToFrankfurt(0)
      route1.size shouldBe 2
    }

    "Munich to Cologne" in {
      val munichToCologne = planner.calculateConnections(new DateTime(), new LocalTime(8, 30), munich, cologne)
      munichToCologne.size shouldBe 1
      val route1 = munichToCologne(0)
      route1.size shouldBe 3
    }
  }

  "Calculate Connections without time" should {
    "Munich to Frankfurt" in {
      val munichToFrankfurt = planner.calculateConnections(new DateTime(), munich, frankfurt)
      munichToFrankfurt.size shouldBe 3
      val route1 = munichToFrankfurt(0)
      route1.size shouldBe 2
    }

    "Munich to Cologne" in {
      val munichToCologne = planner.calculateConnections(new DateTime(), munich, cologne)
      munichToCologne.size shouldBe 3
      val route1 = munichToCologne(0)
      route1.size shouldBe 3
    }
  }
}
