package com.example.apktest.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.math.RoundingMode

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
    @ColumnInfo(name = "apk") var apk: String = ""

    fun calculateApk(): String {
        val percentage = percent.replace("%", "").toBigDecimal().divide(bigDecimal100)
        val apk = volume.toBigDecimal().multiply(percentage).div(price.toBigDecimal())
        return apk.setScale(2, RoundingMode.HALF_EVEN).toString()
    }

    companion object {
        private val bigDecimal100 = BigDecimal.valueOf(100)
    }
}
