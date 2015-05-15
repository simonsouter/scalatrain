/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import java.lang.{IllegalArgumentException => IAE}
import java.math.RoundingMode

import com.typesafe.training.scalatrain.TestData._
import org.joda.money.Money
import org.joda.money.CurrencyUnit._
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.Await
import scala.concurrent.duration.FiniteDuration

class CostSpec extends WordSpec with Matchers {

  "Cost class" should {
    "generate cost properly" in {
      Cost.generateCost(59, 1.0) shouldEqual Money.of(GBP, 2.50)
      Cost.generateCost(69, 1.2) shouldEqual Money.of(GBP, 3.50).multipliedBy(1.2, RoundingMode.DOWN)
      Cost.generateCost(199, 0.5) shouldEqual Money.of(GBP, 9.99).multipliedBy(0.5, RoundingMode.DOWN)
    }
    "convert currencies" in {
      val fixerApi = new FixerIO
      val oneGbp = Money.of(GBP, 1.00)
      val gbp2usd = Await.result(fixerApi.getExchange(USD.toString), FiniteDuration(5, "seconds"))

      Cost.convertCurrency(oneGbp, USD) shouldEqual oneGbp.convertedTo(USD, new java.math.BigDecimal(gbp2usd), RoundingMode.DOWN)
    }
  }

}
