package com.laydybug.util

import play.api.libs.json.{Json, __}

object play_json_test {

  val json = Json parse """{
                          |  "key1" : "value1",
                          |  "key2" : {
                          |    "key21" : 123,
                          |    "key22" : true,
                          |    "key23" : [ "alpha", "beta", "gamma"],
                          |    "key24" : {
                          |      "key241" : 234.123,
                          |      "key242" : "value242"
                          |    }
                          |  },
                          |  "key3" : 234
                          |} """

  val jsonTransformer = (__ \ 'key2 \ 'key23).json.pick


  json.transform(jsonTransformer)

}
