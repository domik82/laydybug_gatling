package com.laydybug.scenarios

import com.laydybug.domain.{Login, Orders}
import io.gatling.core.Predef.{scenario}
import io.gatling.core.Predef._


object order_scenario {

  val make_order = scenario("make_order")
    .exec(Login.login("client", "test"))
    .exec(session => {
      val maybeId = session("myUserId").asOption[String]
      println(maybeId.getOrElse("COULD NOT FIND ID"))
      session
    })
    .exec(Orders.open_orders)
    .pause(1)
    .exec(Orders.get_clients)
    .pause(2)
    .exec(Orders.make_order)
    .exec(session => {
      println(session("created_order").asOption[String].getOrElse("COULD NOT FIND created_order"))
      session
    })
    .exec(Login.log_out)
    .exitBlockOnFail(exec(Login.log_out))
}
