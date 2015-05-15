package com.typesafe.training.scalatrain

import org.joda.time.DateTime

/**
 * Created by charlesrice on 15/05/15.
 */

/**
 * Singleton object that contains a master list of purchases, with additional methods
 */
object TicketMaster {
  private var purchases = List.empty[Purchase]

  def size: Int = purchases.size

  def addPurchase(purchase: Purchase): Unit = {
    purchases = purchases :+ purchase
  }

  def findByEmail(email: String): List[Purchase] = {
    purchases.filter(p => p.user.email == email)
  }
}

/**
 * Instance of a purchase
 * @param user User who purchased this ticket
 * @param from Station user is coming from
 * @param to Station user is going to
 * @param purchasedOn Date and Time that purchase was made
 * @param purchaseType Type of ticket
 */
case class Purchase(user: User, from: Station, to: Station, purchasedOn: DateTime, purchaseType: pType.Value) {

}

case class User(name: Name, email: String)

case class Name(first: String, last: String)

/**
 * pType indicates purchase type. Allows us to extend it later on if needed.
 */
abstract class pType

object pType extends Enumeration(1) {
  val Fastest, Cheapest = Value
}
