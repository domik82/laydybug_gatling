package com.laydybug.util

object Headers {
//  val commonHeader = Map(
//    "Accept" -> "application/json, text/javascript, */*; q=0.01",
//    "Accept-Language" -> "pl-PL,pl;q=0.9,en-US;q=0.8,en;q=0.7",
//    "X-Requested-With" -> "XMLHttpRequest",
//  )

  val commonHeader = Map("X-Requested-With" -> "XMLHttpRequest")

  val headers_4 = Map(
    "Content-Type" -> "application/json;charset=UTF-8",
    "Origin" -> "http://localhost:8080",
    "X-Requested-With" -> "XMLHttpRequest")

  val login_headers_0 = Map(
    "Content-Type" -> "application/x-www-form-urlencoded; charset=UTF-8",
    "Origin" -> "http://localhost:8080",
    "X-Requested-With" -> "XMLHttpRequest")

  val login_headers_1 = Map("Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8")

  val confirm_order = Map(
    "Accept" -> "*/*",
    "Origin" -> "http://localhost:8080")






}
