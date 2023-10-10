package com.example.productsapo.presenter

import com.example.productsapo.api.ApiServiceVariant
import com.example.productsapo.api.modelAPI.modelOrderAPI.JsonOrderAPI
import com.example.productsapo.api.modelAPI.modelOrderAPI.OrderAPI
import com.example.productsapo.contract.OrderContract
import com.example.productsapo.model.Order
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderPresenter(var orderContract: OrderContract) {

    fun getAddOrder(order: Order) {
        val orderAPI = orderConverter(order)

        val jsonOrderAPI = JsonOrderAPI(orderAPI)
        val apiServiceVariant = ApiServiceVariant()
        apiServiceVariant.Retrofit.postOrder(jsonOrderAPI)
            .enqueue(object : Callback<JsonOrderAPI> {
                override fun onResponse(
                    call: Call<JsonOrderAPI>,
                    response: Response<JsonOrderAPI>
                ) {

                }

                override fun onFailure(call: Call<JsonOrderAPI>, t: Throwable) {
                }
            })

    }

    private fun orderConverter(order: Order): OrderAPI {
        return OrderAPI(order)
    }

}