package com.example.apktest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.apktest.db.Product
import com.example.apktest.http.ProductService
import com.example.apktest.http.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var model: ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = ViewModelProvider.AndroidViewModelFactory(application)
            .create(ProductViewModel::class.java)
        model.allProducts.observe(this,
            { products -> products.let { Toast.makeText(application, it.get(5).name, Toast.LENGTH_LONG).show() } })

        getDataFromApi()
    }

    private fun getDataFromApi() {
        val productService = ServiceBuilder.buildService(ProductService::class.java)
        val call = productService.getProducts()
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>,
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { model.insert(it) }
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toast.makeText(applicationContext, t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }
}
