/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import java.lang.{IllegalArgumentException => IAE}

import com.typesafe.training.scalatrain.TestData._
import com.typesafe.training.scalatrain.Routing._
import org.scalatest.{Matchers, WordSpec}

class PathSpec extends WordSpec with Matchers {

  "Paths" should {
    "be sortable by time" in {
      List(ice726Path, ice724Path).sortPathTime shouldEqual List(ice724Path, ice726Path)

    }
    "be sortable by cost" in {
      List(expensivePath, cheapPath).sortPathCost shouldEqual List(cheapPath, expensivePath)
    }
    "calculate the correct size" in {
      ice724Path.size shouldEqual 3
      ice724Path.copy(path = List(ice724Path.path.head)).size shouldEqual 1
    }
    "calculate correct Total Travel Time in minutes" in {
      ice724Path.totalTime shouldEqual 289
      ice726Path.totalTime shouldEqual 312
      Path(List(ice726Munich2Nuremberg)).totalTime shouldEqual ice726Munich2Nuremberg.travelTime
    }
  }
}
