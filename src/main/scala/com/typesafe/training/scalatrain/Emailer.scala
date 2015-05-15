package com.typesafe.training.scalatrain

import org.apache.commons.mail.{SimpleEmail, Email}

/**
 * Created by user on 15/05/15.
 */
class Emailer {

  val hostName = "localhost"
  val smtpPort = 2525

  def sendEmailThankYou(emailAddress: String, route: Path) = {
    val email = new SimpleEmail()
    email.setHostName(hostName)
    email.setSmtpPort(smtpPort)
    email.setFrom("simonsouter@hotmail.com")
    email.setSubject("Thank You - Simon and Chuck's Trains!")
    email.setMsg(messageFromPath(route))
    email.addTo(emailAddress)
    try {
      email.send()
    } catch {
      case e =>
        println("Failed to send email!" + e.getMessage)
    }
  }

  private def messageFromPath(path: Path): String = {
    //    def a = "Leg ${l} - Departing ${a} at ${aa} and arriving into ${b} at ${bb}"
    val message = for {
      a <- path.path
      b = path.path.indexOf(a) + 1
    } yield {
        s"Leg ${b} - Departing ${a.from.name} at ${a.departureTime.time.toString} and arriving into ${a.to.name} at ${a.arrivalTime.time.toString}\n"
      }

    message.reduce(_ + _)
  }
}
