package com.typesafe.training.scalatrain

import com.ning.http.client.AsyncHttpClientConfig
import play.api.libs.json.Json
import play.api.libs.ws.WSRequestHolder
import play.api.libs.ws.ning.NingWSClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 */
class FixerIO {
  val url = "http://api.fixer.io/latest?base=GBP&symbols="

  lazy val builder = new AsyncHttpClientConfig.Builder()

  lazy val wsClient = new NingWSClient(builder.build())

  /**
   * Rest get call to CSMS
   */
  def getExchange(symbol: String): Future[Double] = {
    implicit val rateFormat = Json.format[ExchangeRate]

    val holder: WSRequestHolder = wsClient.url(url + symbol)
      .withHeaders("Accept" -> "application/json")
      .withRequestTimeout(10000)

    val future = holder.get().map(ws => {
      val exch = ws.json.as[ExchangeRate]
      val requiredRate = exch.rates.find(x => x._1 == symbol)

      //TODO fail future?
      requiredRate.map(x => x._2).get
    })
    future
  }
}

case class ExchangeRate(base: String, date: String, rates: Map[String, Double])

