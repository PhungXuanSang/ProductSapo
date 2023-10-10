package com.example.productsapo.model

import com.example.productsapo.api.modelAPI.modelProductAPI.VariantPricesAPI
import java.io.Serializable


class VariantPrice() : Serializable {
    var id : Int? = null
    var value : Double ? = null
    var name : String? = null
    var price_list_id : Int? = null
    var price_list : PriceList? = null

    constructor(variant_Prices: VariantPricesAPI) : this() {
        id = variant_Prices.id
        value = variant_Prices.value
        name = variant_Prices.name
    }

}