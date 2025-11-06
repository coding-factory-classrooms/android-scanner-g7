package com.example.scanner.home

import androidx.lifecycle.ViewModel
import com.example.scanner.OpenFoodFactApi
import com.example.scanner.Product
import com.example.scanner.ProductResponse
import com.example.scanner.Welcome
import com.example.scanner.WikiMediaApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

sealed class MainViewModelState {
    data object Loading : MainViewModelState()
    data class SuccessOFF(val product: Product?) : MainViewModelState()
    data class SuccessWM(val page: String?) : MainViewModelState()
    data class FailureScan(val message: String) : MainViewModelState()
    data class FailureBottle(val message: String) : MainViewModelState()

}

class HomeViewModel(val apiOFF: OpenFoodFactApi, val apiWM: WikiMediaApi) : ViewModel() {

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


                searchExtract(product?.brands)
//                Log.i(TAG, "onResponse: $product")
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
//                Log.e(TAG, "onFailure: ", t)
                State.value = MainViewModelState.FailureScan("error")
            }

        })
    }

    fun searchExtract(name: String) {
        val call = apiWM.getPage()

        call.enqueue(object : Callback<Welcome> {
            override fun onResponse(call: Call<Welcome>, response: Response<Welcome>) {
                if (!response.isSuccessful) {
                    State.value = MainViewModelState.FailureScan("Erreur HTTP ${response.code()}")
                    return
                }

                // Si Query.pages est une List<Page>
                val extractFromList = response.body()?.query?.pages?.firstOrNull()?.extract

                // Si Query.pages est une Map<String, Page>
                val extractFromMap = response.body()?.query?.pages?.values?.firstOrNull()?.extract

                // Choisir l'un ou l'autre : on retourne le premier non-null trouvé
                val extract = extractFromList ?: extractFromMap

                if (extract != null) {
                    State.value = MainViewModelState.SuccessWM(extract)
                } else {
                    State.value = MainViewModelState.FailureScan("Aucun extrait trouvé")
                }
            }

            override fun onFailure(call: Call<Welcome>, t: Throwable) {
                State.value = MainViewModelState.FailureScan("Erreur réseau : ${t.message}")
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