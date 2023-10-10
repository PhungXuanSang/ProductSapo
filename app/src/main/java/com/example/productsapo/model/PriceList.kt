package com.example.productsapo.model

import com.example.productsapo.api.modelAPI.modelProductAPI.PriceListAPI
import java.io.Serializable


class PriceList() : Serializable {
    var id : Int? = null
    var tenantid: Int? = null
    var createdon: String? = null
    var modifiedon: String? = null
    var code : String? = null
    var currencyid: Int? = null
    var name : String? = null
    var iscost: Boolean? = null
    var currencysymbol: String? = null
    var currencyiso: String? = null
    var status : String? = null
    var init : Boolean? = null

    constructor(priceList: PriceListAPI) : this(){
        code = priceList.code
    }

}