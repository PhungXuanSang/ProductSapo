package com.example.productsapo.presenter


import com.example.productsapo.api.ApiServiceVariant
import com.example.productsapo.api.modelAPI.modelProductAPI.ListVariantAPI
import com.example.productsapo.api.modelAPI.modelProductAPI.MetaDataAPI
import com.example.productsapo.api.modelAPI.modelProductAPI.VariantsAPI
import com.example.productsapo.contract.SelectVariantContract
import com.example.productsapo.model.MetaData
import com.example.productsapo.model.OrderLineItem
import com.example.productsapo.model.Variants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectVariantPresenter(val selectVariantContracts: SelectVariantContract) {


    private var currentPage: Int = 1
    private var limit = 10
    fun getListVariant(currentPage : Int){
        val apiServiceVariant = ApiServiceVariant()
        apiServiceVariant.Retrofit.getDataVariant(currentPage,limit,"")
            .enqueue(object : Callback<ListVariantAPI> {
                override fun onResponse(
                    call: Call<ListVariantAPI>,
                    response: Response<ListVariantAPI>
                ) {
                    val data = response.body()
                    if (data != null) {
                        val list = listOrder(data.variants) as MutableList<OrderLineItem>
                        val metaData = metadata(data.metaDataVariant)
                        selectVariantContracts.callListVariant(list, metaData)
                    }

                }
                override fun onFailure(call: Call<ListVariantAPI>, t: Throwable) {
                }

            })

    }
    fun searchVariant(query: String){
        val apiServiceVariant = ApiServiceVariant()
        apiServiceVariant.Retrofit.getDataVariant(currentPage,limit,query)
            .enqueue(object : Callback<ListVariantAPI> {
                override fun onResponse(
                    call: Call<ListVariantAPI>,
                    response: Response<ListVariantAPI>
                ) {
                    val data = response.body()
                    if (data != null) {
                        val list = listOrder(data.variants) as  MutableList<OrderLineItem>

                        val metaData = metadata(data.metaDataVariant)
                        selectVariantContracts.callListVariant(list, metaData)
                    }

                }
                override fun onFailure(call: Call<ListVariantAPI>, t: Throwable) {

                }

            })

    }

    fun listOrder(list: List<VariantsAPI>): List<OrderLineItem> {
        return list.map { variantDTOToOrderLineItem(it) }

    }
    private fun variantDTOToOrderLineItem(dto:VariantsAPI) :OrderLineItem{
        val orderLineItem = OrderLineItem()
        orderLineItem.variant= Variants(dto)
        return orderLineItem
    }


    fun metadata(metaDataAPI: MetaDataAPI): MetaData {
        return MetaData(metaDataAPI)
    }

}