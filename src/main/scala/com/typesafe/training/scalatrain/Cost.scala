/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import java.math.RoundingMode

import org.joda.money.{CurrencyUnit, Money}
import org.joda.money.CurrencyUnit._

import scala.concurrent.Await
import scala.concurrent.duration.FiniteDuration

object Cost {

  lazy val fixerApi = new FixerIO

  /**
   * Given length of a Hop, generate the cost
   * @param minutes Minutes from departure time to arrival time, as an Int
   * @param modifier Double that increases or decreases ticket cost
   * @return Cost object
   */
  def generateCost(minutes: Int, modifier: Double): Money = {
    (minutes match {
      case x if x <= 60 => Money.parse("GBP 2.50")
      case x if x <= 120 => Money.parse("GBP 3.50")
      case x if x > 120 => Money.parse("GBP 9.99")
      case default => Money.parse("GBP 1.00")
    }) multipliedBy(modifier, RoundingMode.DOWN)
  }

  def convertCurrency(value: Money, targetCurrency: CurrencyUnit): Money = {
    val rate = Await.result(fixerApi.getExchange(targetCurrency.toString), FiniteDuration(5, "seconds"))
    value convertedTo(targetCurrency, new java.math.BigDecimal(rate), RoundingMode.DOWN)
  }
}
