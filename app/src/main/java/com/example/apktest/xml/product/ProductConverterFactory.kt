package com.example.apktest.xml.product

import com.example.apktest.db.Product
import com.example.apktest.xml.XmlParser
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ProductConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit,
    ): Converter<ResponseBody, *>? {
        return ProductResponseBodyConverter()
    }
}

class ProductResponseBodyConverter : Converter<ResponseBody, List<Product>>, XmlParser {
    override fun convert(value: ResponseBody): List<Product>? {
        return parse(value.byteStream())
    }
}
