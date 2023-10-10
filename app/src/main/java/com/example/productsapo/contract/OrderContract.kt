package com.example.productsapo.contract

import com.example.productsapo.model.OrderSource

interface OrderContract {

    fun callListSourceId(orderSource: MutableList<OrderSource>)

}