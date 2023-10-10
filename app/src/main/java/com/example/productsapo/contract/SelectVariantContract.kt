package com.example.productsapo.contract

import com.example.productsapo.model.MetaData
import com.example.productsapo.model.OrderLineItem
import com.example.productsapo.model.Variants

interface SelectVariantContract {

    fun callListVariant(listOrderLineItem:   MutableList<OrderLineItem>, metaData: MetaData)
}