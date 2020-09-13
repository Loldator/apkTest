package com.example.apktest.http

import com.example.apktest.xml.product.ProductConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object ServiceBuilder {
    private const val BASE_URL = "https://www.systembolaget.se/"
    private val client = OkHttpClient.Builder().build()
    private val retrofitProductApi = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(ProductConverterFactory())
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofitProductApi.create(service)
    }
}
