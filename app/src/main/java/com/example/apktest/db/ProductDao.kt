package com.example.apktest.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("SELECT CAST(COUNT() AS BIT) FROM product_table")
    fun isEmpty(): LiveData<Boolean>

    @Query("SELECT * FROM product_table ORDER BY apk DESC LIMIT 25")
    fun getAllProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM product_table WHERE number IN (:productIds)")
    fun getProductsByNumbers(productIds: IntArray): LiveData<List<Product>>

    @Query("SELECT * FROM product_table WHERE type LIKE :type LIMIT 25")
    fun getProductsByType(type: String): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(products: List<Product>)
}
