package com.example.apktest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.apktest.db.Product
import com.example.apktest.db.ProductDatabase
import com.example.apktest.db.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProductRepository

    val allProducts: LiveData<List<Product>>

    init {
        val productsDao = ProductDatabase.getDatabase(application).productDao()
        repository = ProductRepository(productsDao)
        allProducts = repository.allProducts
    }

    fun insert(products: List<Product>) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(products)
    }
}
