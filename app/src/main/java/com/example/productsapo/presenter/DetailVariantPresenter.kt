package com.example.productsapo.presenter

import com.example.productsapo.api.ApiServiceProduct
import com.example.productsapo.api.ApiServiceVariant
import com.example.productsapo.api.modelAPI.modelProductAPI.ListProductAPI
import com.example.productsapo.api.modelAPI.modelProductAPI.ListVariantAPI
import com.example.productsapo.api.modelAPI.modelProductAPI.ProductsAPI
import com.example.productsapo.api.modelAPI.modelProductAPI.VariantsAPI
import com.example.productsapo.contract.DetailVariantContract
import com.example.productsapo.model.Products
import com.example.productsapo.model.Variants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailVariantPresenter(var detailVariantContract: DetailVariantContract) {

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
                        list?.let { detailVariantContract.callDetailProduct(it) }
                    }
                }

                override fun onFailure(call: Call<ListProductAPI>, t: Throwable) {
                }
            })
    }

    fun toModelProduct(products: ProductsAPI): Products {
        return Products(products)
    }

    fun detailVariant(productId: Int, variantId: Int) {
        val apiServiceVariant = ApiServiceVariant()

        apiServiceVariant.Retrofit.getDetailVariant(productId, variantId)
            .enqueue(object : Callback<ListVariantAPI> {
                override fun onResponse(
                    call: Call<ListVariantAPI>,
                    response: Response<ListVariantAPI>
                ) {
                    val data = response.body()

                    if (data != null) {
                        val list = data.variant?.let { toModelVariant(it) }
                        list?.let { detailVariantContract.callDetailVariant(it) }
                    }
                }

                override fun onFailure(call: Call<ListVariantAPI>, t: Throwable) {

                }

            })
    }

    fun toModelVariant(variants: VariantsAPI): Variants {
        return Variants(variants)
    }

}