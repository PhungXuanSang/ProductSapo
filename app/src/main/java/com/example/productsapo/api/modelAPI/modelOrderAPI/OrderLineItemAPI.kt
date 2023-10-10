package com.example.productsapo.api.modelAPI.modelOrderAPI

import com.google.gson.annotations.SerializedName

class OrderLineItemAPI {

    @SerializedName("product_id")
    var productId:Int?=null

    @SerializedName("variant_id")
    var variantId:Int?=null

    @SerializedName("price")
    var price:Double?=null

    @SerializedName("quantity")
    var quantity:Double?=null

}