package com.example.productsapo.api.modelAPI.modelOrderAPI

import com.google.gson.annotations.SerializedName

class JsonOrderSourceAPI {
    @SerializedName("order_sources")
    var orderSources:MutableList<OrderSourceAPI>?=null
}