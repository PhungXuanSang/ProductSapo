package com.example.productsapo.api


import com.example.productsapo.api.format.TextObject
import com.example.productsapo.api.interfaces.ProductInterface
import com.example.productsapo.api.interfaces.VariantInterface
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceProduct{

    private val HTTP_CLIENT = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
        val request: Request = chain.request().newBuilder()
            .addHeader(TextObject.SESSION_NAME,TextObject.SESSION_ID_VALUE)
            .build()
        chain.proceed(request)
    }).build()

    val Retrofit = retrofit2.Retrofit.Builder()
        .baseUrl(TextObject.SESSION_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .client(HTTP_CLIENT)
        .build()
        .create(ProductInterface :: class.java)


}
class ApiServiceVariant{

    private val HTTP_CLIENT = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
        val request: Request = chain.request().newBuilder()
            .addHeader(TextObject.SESSION_NAME,TextObject.SESSION_ID_VALUE)
            .build()
        chain.proceed(request)
    }).build()

    val Retrofit = retrofit2.Retrofit.Builder()
        .baseUrl(TextObject.SESSION_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .client(HTTP_CLIENT)
        .build()
        .create(VariantInterface :: class.java)
}