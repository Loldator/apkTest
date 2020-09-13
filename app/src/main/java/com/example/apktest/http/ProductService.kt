package com.example.apktest.http

import com.example.apktest.db.Product
import retrofit2.Call
import retrofit2.http.GET

interface ProductService {
    @GET("api/assortment/products/xml")
    fun getProducts(): Call<List<Product>>
}
