/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import com.typesafe.training.scalatrain.TestData._
import org.joda.time.DateTime
import org.scalatest.{Matchers, WordSpec}

class TicketMasterSpec extends WordSpec with Matchers {

  "TicketMaster" should {
    "add tickets safely" in {
      purchases.foreach(p => {
        planner.ticketMaster.addPurchase(p)
      })

      planner.ticketMaster.size shouldEqual 9
    }
    "should search properly" in {
      planner.ticketMaster.findByEmail("bob@mail.net") shouldEqual List(purchase1)
    }
    "should return empty list if user doesn't exist" in {
      planner.ticketMaster.findByEmail("loopy loops") shouldEqual List.empty[Purchase]
    }
  }

}
