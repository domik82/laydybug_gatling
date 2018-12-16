/**
  * Created by dmumma on 11/19/15.
  */

package com.laydybug.util

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Environment {
  val baseURL = scala.util.Properties.envOrElse("baseURL", "http://localhost:8080")
  val users = scala.util.Properties.envOrElse("numberOfUsers", "5000")
  val maxResponseTime = scala.util.Properties.envOrElse("maxResponseTime", "5000") // in milliseconds

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .inferHtmlResources()
    .acceptHeader("application/json, text/plain, */*")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("pl-PL,pl;q=0.9,en-US;q=0.8,en;q=0.7")
    .userAgentHeader("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36")

  val uri1 = "http://localhost:8080/api"
}
