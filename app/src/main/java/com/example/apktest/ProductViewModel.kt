package com.example.apktest

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.apktest.db.Product
import com.example.apktest.db.ProductDatabase
import com.example.apktest.db.ProductRepository
import com.example.apktest.http.ProductService
import com.example.apktest.http.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProductRepository

    val allProducts: LiveData<List<Product>>
    lateinit var productDetail: LiveData<Product>

    init {
        val productsDao = ProductDatabase.getDatabase(application).productDao()
        repository = ProductRepository(productsDao)
        allProducts = repository.allProducts
    }

    private fun insert(products: List<Product>) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(products)
    }

    fun getProduct(position: Int) {
        allProducts.value?.get(position)?.number?.let { productDetail = repository.get(it) }
    }

    fun getDataFromApi() {
        val productService = ServiceBuilder.buildService(ProductService::class.java)
        val call = productService.getProducts()
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>,
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { insert(it) }
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toast.makeText(getApplication(), t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }
}
