package com.example.productsapo.api.interfaces

import com.example.productsapo.api.modelAPI.modelProductAPI.ListProductAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductInterface {

    @GET("admin/products.json")
     fun getDataProduct(
                       @Query("page") page: Int,
                       @Query("limit") limit: Int,
                       @Query("Query") query: String
    ): Call<ListProductAPI>

    @GET("admin/products/{id}.json")
    fun getDetailProduct(@Path("id") id: Int) : Call<ListProductAPI>

}