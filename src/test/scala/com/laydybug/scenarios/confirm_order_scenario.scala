package com.laydybug

import com.laydybug.domain.{Login, Orders}
import io.gatling.core.Predef.{scenario, _}


object confirm_order_scenario {

  val confirm_order = scenario("confirm_order")
    .exec(_.set("debug", "true"))
    .exitBlockOnFail(exec(Login.log_out))
    .exec(Login.login("employee", "test"))
    .exec(session => {
      println("myUserId: " + session("myUserId").asOption[String].getOrElse("COULD NOT FIND myUserId"))
      //      println("fullUserData: " + session("fullUserData").asOption[String].getOrElse("COULD NOT FIND fullUserData"))
      session
    })
    .exec(Orders.open_orders)
    .exec(Orders.order_list_by_status(0, 1000, "DRAFT"))
//    TODO: Replace static debug code with if like statement
//    TODO: Update bug https://youtrack.jetbrains.net/issue/SCL-12621 about wrong formatting of "loop" executors
//    Sample code below (code compiles properly)
    .doIfEquals("${debug}", "true") {
      exec(session => {
        println("######## DEBUG #########")
        session
      })
    }
    .exec(session => {
      println("")
      println("######## order_list_by_status #########")
      println("order_list_by_status: " + session("order_list_by_status").asOption[String].getOrElse("COULD NOT FIND order_list_by_status"))
      session
    }
    )
//    TODO: feeder doesn't work randomElementFeeder_v3 was created as replacement
//    There is high chance that such code will fail becuse there won't be enough orders to be updated by below chain
    //    .feed(Orders.randomElementFeeder_v2("randomOrderId", "${order_list_by_status}".asInstanceOf[Option[String]]))
    .exec(
    session => {
      println("")
      println("######## set randomOrderId #########")
      val randomOrderIdList = session("order_list_by_status").asOption[String]
      session.set("randomOrderId", Orders.randomElementFeeder_v3(randomOrderIdList).toString())
    })
    .exec(
      session => {
        println("")
        println("######## validate randomOrderId  #########")
        println("randomOrderId:" + session("randomOrderId").asOption[String].getOrElse("COULD NOT FIND randomOrderId"))
        session
      }
    )
    .pause(1)
    .exec(Orders.get_product_details)
    .exec { session => {
      println("")
      println("######## after get_product_details   #########")
      println(session("available_products").asOption[String].getOrElse("COULD NOT FIND available_products"))
      session
    }
    }
    .exec(Orders.get_client_details)
    .exec { session => {
      println("")
      println("######## after get_client_details   #########")
      println("full_client: " + session("full_client").as[String])
      println("client_id: " + session("client_id").as[String])
      session
    }
    }
    .exec(Orders.get_order_details_by_session_value)
    .exec { session => {
      println("")
      println("######## after get_order_details_by_session_value   #########")
      println("get_order_details_by_session_value_BODY: " + session("get_order_details_by_session_value_BODY").as[String])
      println("order_id: " + session("order_id").as[String])
      println("full_order: " + session("full_order").as[String])
      session
    }
    }
    .pause(2)
    .exec { session =>
      session.set("desired_order_status", "ACCEPTED")
    }
    .exec { session => {
      println("")
      println("######## validate set up desired_order_status   #########")
      println("desired_order_status: " + session("desired_order_status").as[String])
      session
    }
    }
    .exec { session =>
      println("")
      println("######## set desired_order_status_body #########")
      val full_order_val = session("full_order").as[String]
      val desired_order_status_val = session("desired_order_status").as[String]
      val desired_order_status_body_val = Orders.update_order_status(full_order_val, "status", desired_order_status_val)
      //      println("desired_order_status_body_val :" + desired_order_status_body_val.toString())
      session.set("desired_order_status_body", desired_order_status_body_val)
    }
    //    .exec { session =>
    //      println("")
    //      println("######## validate set up desired_order_status_body #########")
    //      println(session("desired_order_status_body").as[String])
    //      session
    //    }
    .exec(Orders.change_order_status_by_session_value)
    .exec { session => {
      println("")
      println("######## change_order_status #########")
      println("randomOrderId: " + session("randomOrderId").as[String])
      println("order_id: " + session("order_id").as[String])
      println("full_order: " + session("full_order").as[String])
      println("")
      session
    }
    }
    .exec(Login.log_out)
}
