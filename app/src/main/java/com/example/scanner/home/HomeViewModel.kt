package com.example.scanner.home

import androidx.lifecycle.ViewModel
import com.example.scanner.OpenFoodFactApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

sealed class MainViewModelState {
    data object Loading : MainViewModelState()
    data class Success(val bottle: Product) : MainViewModelState()
    data class FailureScan(val message: String) : MainViewModelState()
    data class FailureBottle(val message: String) : MainViewModelState()

}
data class Product( val barcode: String)
class HomeViewModel(val api: OpenFoodFactApi) : ViewModel() {

    private val State = MutableStateFlow<MainViewModelState>(MainViewModelState.Loading)
    val uiState = State.asStateFlow()

    private val DebugMode = MutableStateFlow(false)
    val isDebugMode = DebugMode.asStateFlow()




    fun onScanResult(scannedCode: String?) {
        if (scannedCode != null) {
            val product = Product(barcode = scannedCode)
            State.value = MainViewModelState.Success(product)
        } else {
            State.value = MainViewModelState.FailureScan("Scan failed")
        }
    }
    fun toggleDebugMode() {
        DebugMode.update { !it }
    }

    fun searchProduct(barcode: String) {


        // Call api
        val call = api.getProduct("1234")


        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                val product = response.body()!!


               // val callMedia = apiMedia.searchPage(product.name)
                stateLiveData.value = com.example.retrofitapp.MainViewModelState.Success(product)
//                Log.i(TAG, "onResponse: $product")
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
//                Log.e(TAG, "onFailure: ", t)
                stateLiveData.value = com.example.retrofitapp.MainViewModelState.Failure("error")
            }

        })
    }
}