package com.example.productsapo.model

import com.example.productsapo.api.modelAPI.modelOrderAPI.OrderAPI
import com.example.productsapo.api.modelAPI.modelOrderAPI.OrderLineItemAPI
import java.io.Serializable

class Order() : Serializable {

    var sourceId : Int? = null
    var status : String? = null
    var orderLineItems : MutableList<OrderLineItem> = mutableListOf()

    constructor(order: OrderAPI) : this(){
        sourceId = order.sourceId
        status = order.status
        orderLineItems.addAll(convertOrder(order.orderLineItems))

    }

    private fun convertOrder(order: List<OrderLineItemAPI>?): List<OrderLineItem>{
        if(order == null) return listOf()
        val tmpList = mutableListOf<OrderLineItem>()
        order.forEach {
            tmpList.add(OrderLineItem(it))
        }
        return tmpList
    }
    fun getTotalTaxOrder() : Double{
        return orderLineItems.sumOf { it.getTotalTaxOneOrder() }
    }

    fun getTotalQuantity():Double{
        return orderLineItems.sumOf { it.quantity }
    }
    fun getTotalAmountOrder():Double{
        return orderLineItems.sumOf { it.getTotalAmountOneOrder() }
    }

    fun getTotalMoney():Double{
        return orderLineItems.sumOf { it.getTotalMoney() }
    }

}
