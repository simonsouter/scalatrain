package com.typesafe.training.scalatrain

import org.scalatest.{Matchers, WordSpec, FunSuite}

import scala.concurrent.Await
import scala.concurrent.duration.{FiniteDuration, Duration}

/**
 * Created by user on 15/05/15.
 */
class FixerIOSpec extends WordSpec with Matchers {
  "FixerIO api" should {
    "find usd rate" in {
      val io = new FixerIO

      val resp = Await.result(io.getExchange("USD"), FiniteDuration(5, "seconds"))
      resp < 2 shouldBe true
      resp > 0 shouldBe true
    }
  }
}
