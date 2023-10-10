package com.example.productsapo.api.modelAPI.modelProductAPI

import com.google.gson.annotations.SerializedName

data class ImageAPI (

        var id : Int,
        var size : Double,
        var createdon: String,
        var modifiedon: String,
        var path : String,
        @SerializedName("full_path")
        var fullpath: String,
        var filename: String,
        var isdefault: Boolean,
        var position : Int

    )
