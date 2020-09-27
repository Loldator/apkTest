package com.example.apktest.db

import androidx.lifecycle.LiveData

class ProductRepository(private val productDao: ProductDao) {
    val allProducts: LiveData<List<Product>> = productDao.getAllProducts()

    fun insert(products: List<Product>) {
        productDao.insertProducts(products)
    }

    fun get(number: String): LiveData<Product> {
        return productDao.getProduct(number)
    }
}
