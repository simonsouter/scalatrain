package com.typesafe.training.scalatrain

import com.ning.http.client.AsyncHttpClientConfig
import com.typesafe.config.{ConfigFactory, Config}
import play.api.libs.ws.ning.NingWSClient

/**
 * Created by user on 14/05/15.
 */
class FixerIO {
  val config: Config = ConfigFactory.load

  lazy val builder = new AsyncHttpClientConfig.Builder()

  lazy val wsClient = new NingWSClient(builder.build())
}
