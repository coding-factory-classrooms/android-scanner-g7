package com.example.scanner

import  retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OpenFoodFactApi {
    @GET("product/{id}.json")
    fun getProduct(@Path("id") barcode: String) : Call<ProductResponse>
}