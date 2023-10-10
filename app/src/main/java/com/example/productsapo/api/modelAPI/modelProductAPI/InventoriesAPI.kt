package com.example.productsapo.api.modelAPI.modelProductAPI

import com.google.gson.annotations.SerializedName

data class InventoriesAPI(
    @SerializedName("location_id")
    var locationid: Int,
    @SerializedName("variant_id")
    var variantid: Int,
    @SerializedName("mac")
    var mac : Double,
    @SerializedName("amount")
    var amount : Int?,
    @SerializedName("on_hand")
    var onhand: Double,
    //
    @SerializedName("available")
    var available : Double,
    @SerializedName("committed")
    var committed : Double,
    @SerializedName("incoming")
    var incoming : Double,
    @SerializedName("onway")
    var onway : Double,
    @SerializedName("min_value")
    var minvalue: Double,
    @SerializedName("max_value")
    var maxvalue: Double,
    @SerializedName("bin_location")
    var binlocation: String,
    @SerializedName("wait_to_pack")
    var waittopack: Double,
    @SerializedName("modified_on")
    var modifiedon: String


)