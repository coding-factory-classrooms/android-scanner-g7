package com.example.scanner

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WikipediaApi {
    @GET("page/summary/{title}")
    fun getSummary(@Path("title") title: String?): Call<Welcome>
}