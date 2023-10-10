package com.example.productsapo.model

import com.example.productsapo.api.modelAPI.modelProductAPI.MetaDataAPI
import java.io.Serializable


class MetaData() : Serializable {
     var total : Int = 0
     var page : Int = 0
     var limit : Int = 0

    constructor(metaData : MetaDataAPI) : this() {
        total = metaData.total
        page = metaData.page
        limit = metaData.limit
    }


 }