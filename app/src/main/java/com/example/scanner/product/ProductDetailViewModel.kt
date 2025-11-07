package com.example.scanner.product

import androidx.lifecycle.ViewModel
import com.example.scanner.Description
import com.example.scanner.WikipediaApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailViewModel(val apiWM: WikipediaApi) : ViewModel() {

    private val _extractState = MutableStateFlow<String?>(null)
    val extractState: StateFlow<String?> = _extractState

    fun searchExtract(name: String?) {
        val call = apiWM.getSummary(name)
        call.enqueue(object : Callback<Description> {
            override fun onResponse(call: Call<Description>, response: Response<Description>) {
                val extract = response.body()
                _extractState.value = extract?.extract
            }

            override fun onFailure(call: Call<Description>, t: Throwable) {
                _extractState.value = "Erreur lors du chargement"
            }
        })
    }


}