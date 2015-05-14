/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import java.lang.{IllegalArgumentException => IAE}

import com.typesafe.training.scalatrain.TestData._
import org.scalatest.{Matchers, WordSpec}

class CostSpec extends WordSpec with Matchers {

  "Cost class" should {
    "initialise" in {
      new Cost shouldEqual Cost(0,0)
    }
    "multiply correctly" in {
      Cost(2, 50) * 2 shouldEqual Cost(5)
    }
    "add correctly" in {
      Cost(3, 49) + Cost(1, 51) shouldEqual Cost(5)
    }
    "complain of negative or impossible values" in {
      an[IAE] should be thrownBy Cost(1, 999)
      an[IAE] should be thrownBy Cost(-5, 50)
    }
  }

}
