package com.example.scanner.home

import androidx.lifecycle.ViewModel
import com.example.scanner.OpenFoodFactApi
import com.example.scanner.Product
import com.example.scanner.ProductResponse
import com.example.scanner.Welcome
import com.example.scanner.WikipediaApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

sealed class MainViewModelState {
    data object Loading : MainViewModelState()
    data class SuccessOFF(val product: Product?) : MainViewModelState()
    data class SuccessWM(val extract: String?) : MainViewModelState()
    data class FailureScan(val message: String) : MainViewModelState()
    data class FailureBottle(val message: String) : MainViewModelState()

}

class HomeViewModel(val apiOFF: OpenFoodFactApi, val apiWM: WikipediaApi) : ViewModel() {

    private val State = MutableStateFlow<MainViewModelState>(MainViewModelState.Loading)
    val uiState = State.asStateFlow()

    private val DebugMode = MutableStateFlow(false)
    val isDebugMode = DebugMode.asStateFlow()


    fun searchProduct(barcode: String) {


        // Call api
        val call = apiOFF.getProduct(barcode)


        call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                val product = response.body()?.product

                println(product?.brands)
                searchExtract(product?.brands)
//                Log.i(TAG, "onResponse: $product")
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
//                Log.e(TAG, "onFailure: ", t)
                State.value = MainViewModelState.FailureScan("error")
            }

        })
    }

    fun searchExtract(name: String?) {

        // Call api
        val call = apiWM.getSummary(name)
        println(call.request())

        call.enqueue(object : Callback<Welcome> {
            override fun onResponse(call: Call<Welcome>, response: Response<Welcome>) {
                val extract = response.body()
                println(extract)
                State.value = MainViewModelState.SuccessWM(extract?.extract)
//                Log.i(TAG, "onResponse: $product")
            }

            override fun onFailure(call: Call<Welcome>, t: Throwable) {
//                Log.e(TAG, "onFailure: ", t)
                State.value = MainViewModelState.FailureScan("error")
            }

        })
    }

//    fun onScanResult(scannedCode: String?) {
//        if (scannedCode != null) {
//            val product = Product(id = scannedCode)
//
//            State.value = MainViewModelState.Success(product)
//
//        } else {
//            State.value = MainViewModelState.FailureScan("Scan failed")
//        }
//    }
    fun toggleDebugMode() {
        DebugMode.update { !it }
    }


}