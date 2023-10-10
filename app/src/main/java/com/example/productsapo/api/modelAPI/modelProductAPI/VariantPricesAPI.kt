package com.example.productsapo.api.modelAPI.modelProductAPI

data class VariantPricesAPI(
    var id : Int,
    var value : Double,
    var includedtaxprice: Int,
    var name : String,
    var pricelistid: Int,
    var pricelist: PriceListAPI
)
