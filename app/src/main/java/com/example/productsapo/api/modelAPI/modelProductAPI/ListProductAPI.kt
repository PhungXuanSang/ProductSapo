package com.example.productsapo.api.modelAPI.modelProductAPI


import com.google.gson.annotations.SerializedName

data class ListProductAPI(

    @SerializedName("products")
    var products: MutableList<ProductsAPI>,

    @SerializedName("metadata")
    var metaDataProduct: MetaDataAPI,

    var product: ProductsAPI?=null
)
