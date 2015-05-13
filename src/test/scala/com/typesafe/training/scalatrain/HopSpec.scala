/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import java.lang.{IllegalArgumentException => IAE}

import com.typesafe.training.scalatrain.TestData._
import org.scalatest.{Matchers, WordSpec}

class HopSpec extends WordSpec with Matchers {

  "Train ice724" should {
    "have hop from Munich to Nuremberg" in {
      ice724.hops.contains(ice724Munich2Nuremberg) shouldEqual true
      ice724.hops.find(hop => hop.to == nuremberg).get.arrivalTime shouldEqual Some(ice724NurembergTime)
    }
    "not stop in Essen" in {
      ice724.timeAt(essen) shouldEqual None
    }
  }

  "Creating a Hop" should {
    "throw an exception for ice724 hopping from Cologne to Essen" in {
      an[IAE] should be thrownBy Hop(cologne, essen, ice724)
    }
  }

}
