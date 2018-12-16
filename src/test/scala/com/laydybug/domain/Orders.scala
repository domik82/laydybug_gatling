package com.laydybug.domain

import com.laydybug.util.Headers
import com.laydybug.util.Headers.{commonHeader, headers_4}
import io.gatling.core.Predef.{jsonPath, _}
import io.gatling.http.Predef.{http, status, _}
import play.api.libs.json._

import scala.util.Random

object Orders {

  val rnd = new Random

  val order_list = http("orders_list")
    .get("/api/orders/?page=0&size=10")
    .headers(Headers.commonHeader)
    .check(status.is(200))
    .check(jsonPath("$").saveAs("first_orders_page"))

  def order_list_by_status(page_number: Int, page_size: Int, order_status: String) = http("orders_list")
    .get("/api/orders/?page=" + page_number.toString() + "&size=" + page_size)
    .headers(Headers.commonHeader)
    .check(jsonPath("$.content[?(@.status == '" + order_status + "')].id").findAll.transform(_.mkString(",").toSeq).saveAs("order_list_by_status"))
    .check(status.is(200))

  def getRandomElement(list: List[String], random: Random): String =
    list(random.nextInt(list.length))


  def randomElementFeeder(elementName: String, obtainedList: List[String]): Iterator[Map[String, String]] = {
    Iterator.continually(Map(elementName -> getRandomElement(obtainedList, rnd)))
  }

  def randomElementFeeder_v2(elementName: String, obtainedList: Option[String]): Iterator[Map[String, String]] = {
    Iterator.continually(Map(elementName -> getRandomElement(obtainedList.toList, rnd)))
  }

  def randomElementFeeder_v3(obtainedList: Option[String]): String = {
    val elemList = obtainedList.map(_.split(",")).toList.flatten
    val rnd_element = elemList(rnd.nextInt(elemList.size))
    return rnd_element.toString().trim()
  }

  val open_orders = http("open_orders")
    .get("/api/select/statuses")
    .headers(Headers.commonHeader)
    .resources(order_list)
    .check(status.is(200))

  val get_products = http("products")
    .get("/api/select/products")
    .headers(Headers.commonHeader)
    .check(status.is(200))

  val get_clients = http("clients")
    .get("/api/select/clients")
    .headers(Headers.commonHeader)
    .resources(get_products)
    .check(status.is(200))

  val make_order = http("place_order")
    .post("/api/orders/")
    .headers(Headers.headers_4)
    .body(RawFileBody("order_request_example.txt"))
    .resources(open_orders)
    .check(jsonPath("$").saveAs("created_order"))
    .check(status.is(200))

  val get_client_details = http("get_client_details")
    .get("/api/select/clients")
    .headers(commonHeader)
    //          .check(bodyBytes.is(RawFileBody("sample_client_response.txt")))
    .check(jsonPath("$").saveAs("full_client"))
    .check(jsonPath("$..id").count.is(1))
    .check(jsonPath("$..id").saveAs("client_id"))
    .check(status.is(200))


  val get_product_details = http("open_order")
    .get("/api/select/products")
    .headers(commonHeader)
    .check(jsonPath("$").saveAs("available_products"))
    .check(status.is(200))

  val get_order_details_by_session_value = http("get_order_details_by_session_value")
    .get("/api/orders/%s".format("${randomOrderId}"))
    .headers(commonHeader)
    //      .check(bodyBytes.is(RawFileBody("sample_order_response.txt")))
    .check(bodyString.saveAs("get_order_details_by_session_value_BODY"))
    .check(jsonPath("$").exists)
    .check(jsonPath("$").saveAs("full_order"))
    .check(jsonPath("$.id").exists)
    .check(jsonPath("$.id").saveAs("order_id"))
    .check(status.is(200))


  def update_order_status(raw_json: String, key: String, value: String): String = {
    val json = Json parse raw_json
    val updatedJson = json.as[JsObject] ++ Json.obj(key -> value)
    return Json.stringify(updatedJson)
  }

  val change_order_status_by_session_value = http("change_order_status")
    .put("/api/orders/%s".format("${randomOrderId}"))
    .headers(headers_4)
    .body(StringBody("${desired_order_status_body}"))
    .resources(open_orders)
    .check(jsonPath("$.status").is("${desired_order_status}"))
    .check(status.is(200))

}

