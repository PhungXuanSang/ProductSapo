package com.example.productsapo.api.modelAPI.modelProductAPI

import com.google.gson.annotations.SerializedName

data class ListVariantAPI (

    @SerializedName("variants")
    var variants: MutableList<VariantsAPI>,

    @SerializedName("metadata")
    var metaDataVariant : MetaDataAPI,

    var variant: VariantsAPI?=null
)