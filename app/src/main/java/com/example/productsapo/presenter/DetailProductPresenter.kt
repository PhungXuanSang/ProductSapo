package com.example.productsapo.presenter


import com.example.productsapo.api.ApiServiceProduct
import com.example.productsapo.api.modelAPI.modelProductAPI.ListProductAPI
import com.example.productsapo.api.modelAPI.modelProductAPI.ProductsAPI
import com.example.productsapo.contract.DetailProductContract
import com.example.productsapo.model.Products
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailProductPresenter(var detailProductContract: DetailProductContract) {


    fun detailProduct(id: Int) {
        val apiServiceProduct = ApiServiceProduct()

        apiServiceProduct.Retrofit.getDetailProduct(id)
            .enqueue(object : Callback<ListProductAPI> {
                override fun onResponse(
                    call: Call<ListProductAPI>,
                    response: Response<ListProductAPI>
                ) {
                    val data = response.body()

                    if (data != null) {

                        val list = data.product?.let { toModelProduct(it) }
                        list?.let { detailProductContract.callDetailProduct(it) }
                    }
                }

                override fun onFailure(call: Call<ListProductAPI>, t: Throwable) {
                }
            })
    }

    fun toModelProduct(products: ProductsAPI): Products {
        return Products(products)
    }

}