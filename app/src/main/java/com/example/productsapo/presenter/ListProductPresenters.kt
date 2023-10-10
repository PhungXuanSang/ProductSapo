package com.example.productsapo.presenter

import android.util.Log
import com.example.productsapo.api.ApiServiceProduct
import com.example.productsapo.api.ApiServiceVariant
import com.example.productsapo.api.modelAPI.modelProductAPI.ListProductAPI
import com.example.productsapo.api.modelAPI.modelProductAPI.ListVariantAPI
import com.example.productsapo.api.modelAPI.modelProductAPI.MetaDataAPI
import com.example.productsapo.api.modelAPI.modelProductAPI.ProductsAPI
import com.example.productsapo.api.modelAPI.modelProductAPI.VariantsAPI
import com.example.productsapo.contract.ListProductContract
import com.example.productsapo.model.MetaData
import com.example.productsapo.model.Products
import com.example.productsapo.model.Variants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListProductPresenters( var listProductContract : ListProductContract)  {



    private var currentPage: Int = 1
    private var limit = 10

    fun getListProduct(currentPage : Int){

        val apiServiceProduct = ApiServiceProduct()


        apiServiceProduct.Retrofit.getDataProduct(currentPage, limit,"")
            .enqueue(object : Callback<ListProductAPI> {
                override fun onResponse(
                    call: Call<ListProductAPI>,
                    response: Response<ListProductAPI>
                ) {
                    val data = response.body()

//                    Log.d("TAG", "onResponse: $data")

                    if (data != null) {
                        val list = listProduct(data.products) as MutableList<Products>
                        val metaData = metadata(data.metaDataProduct)
                        listProductContract.callListProduct(list,metaData)
                    }
                }
                override fun onFailure(call: Call<ListProductAPI>, t: Throwable) {
                    Log.d("failed", "đ")
                }

            }
             )



    }

    fun getListVariant(currentPage : Int){
        val apiServiceVariant = ApiServiceVariant()
        apiServiceVariant.Retrofit.getDataVariant(currentPage,limit,"")
            .enqueue(object : Callback<ListVariantAPI> {
            override fun onResponse(
                call: Call<ListVariantAPI>,
                response: Response<ListVariantAPI>
            ) {
                val data = response.body()
                Log.d("TAG", "onResponse: $data")
                if (data != null) {
                    val list = listVariant(data.variants) as MutableList<Variants>
                    val metaData = metadata(data.metaDataVariant)
                    listProductContract.callListVariant(list, metaData)
                }

            }
            override fun onFailure(call: Call<ListVariantAPI>, t: Throwable) {
            }

        })

    }
    fun searchProduct(query: String){
     val apiServiceProduct = ApiServiceProduct()
     apiServiceProduct.Retrofit.getDataProduct(currentPage,limit,query)
         .enqueue(object : Callback<ListProductAPI> {
             override fun onResponse(
                 call: Call<ListProductAPI>,
                 response: Response<ListProductAPI>
             ) {
                 val data = response.body()
                  val list = data?.let { listProduct(it.products) } as MutableList<Products>
                     val metaData = metadata(data.metaDataProduct)
                     listProductContract.callListProduct(list,metaData)

             }
             override fun onFailure(call: Call<ListProductAPI>, t: Throwable) {
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
                        val list = listVariant(data.variants) as MutableList<Variants>

                        val metaData = metadata(data.metaDataVariant)
                        listProductContract.callListVariant(list, metaData)
                    }

                }
                override fun onFailure(call: Call<ListVariantAPI>, t: Throwable) {

                }

            })

    }

    fun listProduct(list: List<ProductsAPI>): List<Products> {
        return list.map { Products(it) }
    }

    fun listVariant(list: List<VariantsAPI>): List<Variants> {
        return list.map { Variants(it) }
    }

    fun metadata(metaDataAPI: MetaDataAPI): MetaData {
        return MetaData(metaDataAPI)
    }


}