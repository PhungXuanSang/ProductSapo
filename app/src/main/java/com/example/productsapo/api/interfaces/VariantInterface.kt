package com.example.productsapo.api.interfaces


import com.example.productsapo.api.modelAPI.modelOrderAPI.JsonOrderAPI
import com.example.productsapo.api.modelAPI.modelProductAPI.ListVariantAPI
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface VariantInterface {

    @GET("admin/variants/search.json")
    fun getDataVariant(
                @Query("page") page : Int,
                @Query("limit")limit: Int,
                @Query("Query") query: String

    ): Call<ListVariantAPI>

    @GET("admin/products/{product_id}/variants/{variant_id}.json")
    fun getDetailVariant(@Path("product_id") productId: Int, @Path("variant_id") variantId: Int): Call<ListVariantAPI>

    @Headers("X-Sapo-Client: iOS"
        ,"X-Sapo-LocationId:286483","Content-Type:application/json")
    @POST("admin/orders.json")
    fun postOrder(@Body order: JsonOrderAPI):Call<JsonOrderAPI>
}