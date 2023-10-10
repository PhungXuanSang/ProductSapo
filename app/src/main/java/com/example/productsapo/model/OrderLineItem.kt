package com.example.productsapo.model

import com.example.productsapo.api.modelAPI.modelOrderAPI.OrderLineItemAPI


class OrderLineItem : java.io.Serializable{

    var variant=Variants()
    var quantity:Double=0.0

    constructor(orderLineItem: OrderLineItemAPI): this(){
        quantity = orderLineItem.quantity!!

    }
    constructor()
        fun getTotalAmountOneOrder():Double{
            return variant.variantretailprice * quantity
        }
    fun getTotalTaxOneOrder():Double{
        var totalTax=0.0
        if(variant.outputVatRate!=null){
            totalTax=getTotalAmountOneOrder()* variant.outputVatRate!! /100
        }
        return totalTax
    }
    fun getTotalMoney():Double{
        var totalMoney=0.0
        totalMoney = if(variant.taxIncluded){
            getTotalTaxOneOrder()+getTotalAmountOneOrder()
        }else{
            getTotalAmountOneOrder()
        }
        return totalMoney
    }

}