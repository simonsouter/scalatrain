/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import TestData._
import java.lang.{IllegalArgumentException => IAE}
import org.joda.time.DateTime
import org.scalatest.{Matchers, WordSpec}

class TrainSpec extends WordSpec with Matchers {

  "Train ice724" should {
    "stop in Nurember" in {
      ice724.timeAt(nuremberg) shouldEqual Some(ice724NurembergTime)
    }
    "not stop in Essen" in {
      ice724.timeAt(essen) shouldEqual None
    }
  }

  "Train ice726" should {
    "stop in Munich" in {
      ice726.timeAt(munich) shouldEqual Some(ice726MunichTime)
    }
    "not stop in Cologne" in {
      ice726.timeAt(cologne) shouldEqual None
    }
  }

  "Creating a Train" should {
    "throw an IllegalArgumentException for a schedule with 0 or 1 elements" in {
      an[IAE] should be thrownBy Train(InterCityExpress(724), Vector(), new DateTime())
      an[IAE] should be thrownBy Train(InterCityExpress(724), Vector(ice724MunichTime -> munich), new DateTime())
    }
  }

  "stations" should {
    "be initialized correctly" in {
      ice724.stations shouldEqual Vector(munich, nuremberg, frankfurt, cologne)
    }
  }

  "back2backStations" should {
    "be calculated correctly" in {
      ice724.backToBackStations shouldEqual Seq((munich, nuremberg), (nuremberg, frankfurt), (frankfurt, cologne))
      ice726.backToBackStations shouldEqual Seq((munich, nuremberg), (nuremberg, frankfurt), (frankfurt, essen))
    }
  }

  "RunsOnDate" should {
    "ice724 doesnt run on exception" in {
      ice724.runsOnDate(DateTime.parse("2015-05-13T07:00:00Z")) shouldBe false
    }
    "ice724 runs on Monday" in {
      ice724.runsOnDate(DateTime.parse("2015-05-11T07:00:00Z")) shouldBe true
    }
  }
}
