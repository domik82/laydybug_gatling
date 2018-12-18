package com.laydybug.simulation

import com.laydybug.confirm_order_scenario
import com.laydybug.scenarios.order_scenario
import com.laydybug.util.Environment
import io.gatling.core.Predef.{atOnceUsers, _}

import scala.concurrent.duration._

class order extends Simulation {

  //  Implemented scenario has two Actors:
  //  As a client place order for 2 products
  //  As a employee accept this order


  //  TODO: 1. In ideal world such scenario should work in a fashion where users would have random orders with random product amount
  //        This is not the case here - I used order_request_example.txt to execute request with 2 products
  //        I thought that learning how to create proper json is not the case here (it can be done other time)
  //        2. Confirm product by employee has "assumption"/"workaround" - because lack of time I didn't had chance to implement paging
  //        Instead I used trick to get 500/1000 orders on single page. The problem with that approach is that once all orders on first page
  //        will be confirmed test will start to fail
  //        3. There is no proper exit/fail fast strategy - sometimes test fails in random manner and threads keep working
  //        This should be fixed to avoid exceptions in build log
  //        4. Observations during programming can be found in timeEaters.txt file. Right now I would say that writing tests
  //        in this framework is complicated especially when some "random" issue will show up.
  //        Scala tutorial would probably be helpful.
  //        There were multiple issues without any clear solution that could be easily found in documentation
  //

  setUp(
    order_scenario.make_order.inject(
      atOnceUsers(10),
      nothingFor(10 seconds),
      rampUsers(10) during (30 seconds),
      constantUsersPerSec(50) during (30 seconds),
      rampUsers(100) during (5 seconds),
    ).protocols(Environment.httpProtocol),

//    Figured out how to use holdfor - below is another way of injecting the users.
//    Below are are random valuesUncomment to test.
//    order_scenario.make_order.inject(
//      constantUsersPerSec(500) during (30 seconds))
//      .protocols(Environment.httpProtocol)
//      .throttle(
//        reachRps(100) in (10 seconds),
//        holdFor(1 minute),
//        jumpToRps(50),
//        holdFor(2 minute)
//      ),


    confirm_order_scenario.confirm_order.inject(
      nothingFor(10 seconds),
      atOnceUsers(5),
      nothingFor(10 seconds),
      rampUsers(10) during (30 seconds),
      constantUsersPerSec(50) during (30 seconds),
      rampUsers(100) during (5 seconds)
    ).protocols(Environment.httpProtocol)


  ).maxDuration(5 minutes)

}