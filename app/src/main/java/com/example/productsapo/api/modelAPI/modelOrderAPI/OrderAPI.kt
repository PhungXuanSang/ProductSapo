package com.example.productsapo.api.modelAPI.modelOrderAPI


import com.example.productsapo.model.Order
import com.google.gson.annotations.SerializedName

class OrderAPI(order: Order) {

    @SerializedName("source_id")
    var sourceId:Int?=null

    @SerializedName("status")
    var status:String?=null

    @SerializedName("order_line_items")
    var orderLineItems: MutableList<OrderLineItemAPI> = mutableListOf()
}