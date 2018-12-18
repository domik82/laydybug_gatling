package com.laydybug.domain


import io.gatling.core.Predef.{StringBody, jsonPath}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import com.laydybug.util.Headers
import io.gatling.http.Predef.{http, status}
import io.gatling.http.request.builder.HttpRequestBuilder

object Login {

  def login(username: String, password: String): HttpRequestBuilder = {
    return http("login")
      .post("/login")
      .headers(Headers.login_headers_0)
      .formParam("username", username)
      .formParam("password", password)
      .resources(get_icon)
      .check(jsonPath("$.id").saveAs("myUserId"))
      .check(jsonPath("$").saveAs("fullUserData"))
      .check(status.is(200))
  }

  val get_icon = http("get_icon")
    .get("/favicon.ico")
    .headers(Headers.login_headers_1)


  var log_out = http("logout")
    .post("/logout")
    .headers(Headers.headers_4)
    .body(StringBody("""{}"""))
    .check(status.is(404))
}
