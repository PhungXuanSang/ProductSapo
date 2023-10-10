package com.example.productsapo.model

import com.example.productsapo.api.modelAPI.modelProductAPI.InventoriesAPI
import java.io.Serializable


class Inventories() : Serializable
    {
         var variantid: Int? = null
        var onhand: Double? = null
        var available : Double? = null


        constructor(inventories: InventoriesAPI) : this() {
            available = inventories.available
            onhand = inventories.onhand
            variantid = inventories.variantid
        }

    }

