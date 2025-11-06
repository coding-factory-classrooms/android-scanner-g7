package com.example.scanner

import  retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


// l interface en quesiton en gros c'est un ajout à l'url en gros ce met en plus de l'url et tu ajoute ca derrire
interface OpenFoodFactApi {
    @GET("product/{id}.json")
 //   et ca c'est pour appeller la requete dans le ViewModel'
    fun getProduct(@Path("id") barcode: String) : Call<ProductResponse>
}