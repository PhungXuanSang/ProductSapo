package com.example.productsapo.api.modelAPI.modelProductAPI

import com.google.gson.annotations.SerializedName

data class PriceListAPI(
    var id : Int,
    var tenantid: Int,
    var createdon: String,
    var modifiedon: String,
    @SerializedName("code")
    var code : String,
    var currencyid: Int,
    var name : String,
    var iscost: Boolean,
    var currencysymbol: String,
    var currencyiso: String,
    var status : String,
    var init : Boolean
)
