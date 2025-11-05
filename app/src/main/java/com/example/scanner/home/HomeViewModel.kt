package com.example.scanner.home

import androidx.compose.material3.Text
import androidx.lifecycle.ViewModel
import com.example.scanner.OpenFoodFactApi
import com.example.scanner.Product
import com.example.scanner.ProductResponse
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
    data class Success(val product: Product?) : MainViewModelState()
    data class FailureScan(val message: String) : MainViewModelState()
    data class FailureBottle(val message: String) : MainViewModelState()

}

class HomeViewModel(val api: OpenFoodFactApi) : ViewModel() {

    private val State = MutableStateFlow<MainViewModelState>(MainViewModelState.Loading)
    val uiState = State.asStateFlow()

    private val DebugMode = MutableStateFlow(false)
    val isDebugMode = DebugMode.asStateFlow()


    fun searchProduct(barcode: String) {


        // Call api
        val call = api.getProduct(barcode)


        call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                val product = response.body()?.product


                // val callMedia = apiMedia.searchPage(product.name)
                State.value = MainViewModelState.Success(product)
//                Log.i(TAG, "onResponse: $product")
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
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