/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import java.lang.{IllegalArgumentException => IAE}

import com.typesafe.training.scalatrain.TestData._
import org.scalatest.{Matchers, WordSpec}

class PathSpec extends WordSpec with Matchers {

  "Path" should {
    "be sortable by time" in {
      ice724Path.orderByTravelTime shouldEqual ice724PathByTime
    }
    "be sortable by cost" in {
      ice726Path.orderByCost shouldEqual ice726PathByCost
    }
  }

}
