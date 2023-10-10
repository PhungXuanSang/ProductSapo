package com.example.productsapo.api.modelAPI.modelProductAPI

import com.google.gson.annotations.SerializedName

data class OptionsAPI(
    @SerializedName("id")
    var id : Int,
    @SerializedName("name")
    var name : String,
    @SerializedName("position")
    var position : Int,
    @SerializedName("values")
    var values : MutableList<String>
)
