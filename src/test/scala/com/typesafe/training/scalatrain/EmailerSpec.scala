package com.typesafe.training.scalatrain

import org.scalatest.{Matchers, WordSpec}

/**
 * Created by user on 15/05/15.
 */
class EmailerSpec extends WordSpec with Matchers {
  val emailer = new Emailer

  "Email" should {
    "Send Thankyou mail in" in {
      emailer.sendEmailThankYou("test@test.com", Path(List(
        Hop(TestData.munich, TestData.nuremberg,TestData.ice724),
        Hop(TestData.nuremberg, TestData.frankfurt,TestData.ice724))))
    }
  }
}
