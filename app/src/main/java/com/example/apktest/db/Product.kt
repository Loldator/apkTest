package com.example.apktest.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product(@PrimaryKey var number: String) {
    @ColumnInfo(name = "name") var name: String = ""
    @ColumnInfo(name = "name_2") var name2: String = ""
    @ColumnInfo(name = "price") var price: String = ""
    @ColumnInfo(name = "volume") var volume: String = ""
    @ColumnInfo(name = "litre_price") var litrePrice: String = ""
    @ColumnInfo(name = "percent") var percent: String = ""
    @ColumnInfo(name = "product_group") var productGroup: String = ""
    @ColumnInfo(name = "type") var type: String = ""
}
