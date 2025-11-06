package com.example.scanner

import retrofit2.Call
import retrofit2.http.GET

interface WikiMediaApi {
    @GET("api.php")
    fun getPage(
        @Query("format") format: String = "json",
        @Query("formatversion") version: Int = 2,
        @Query("action") action: String = "query",
        @Query("prop") prop: String = "extracts",
        @Query("titles") name: String,
        @Query("explaintext") explaintext: Int = 1,
        @Query("exintro") exintro: Int = 1
    ): Call<Welcome>
}