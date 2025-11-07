package com.example.scanner.home

import androidx.lifecycle.ViewModel
import com.example.scanner.OpenFoodFactApi
import com.example.scanner.Product
import com.example.scanner.ProductResponse
import com.example.scanner.Description
import com.example.scanner.WikipediaApi
import io.paperdb.Paper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.emptyList

sealed class MainViewModelState {
    data object Loading : MainViewModelState()
    data class SuccessOFF(val product: Product?) : MainViewModelState()
    data class SuccessWM(val extract: String?) : MainViewModelState()
    data class FailureScan(val message: String) : MainViewModelState()
    data class FailureBottle(val message: String) : MainViewModelState()

}

class HomeViewModel(val apiOFF: OpenFoodFactApi) : ViewModel() {

    private val State = MutableStateFlow<MainViewModelState>(MainViewModelState.Loading)
    val uiState = State.asStateFlow()

    private val DebugMode = MutableStateFlow(false)
    val isDebugMode = DebugMode.asStateFlow()

// ca c'est les fonctions de call api, celle la c 'est la première
    fun searchProduct(barcode: String) {


        // Call api
        val call = apiOFF.getProduct(barcode)

        //je sais plus c'est quoi précisement enqueue mais c'est un sorte de execute mais particulier
        call.enqueue(object : Callback<ProductResponse> {
            // ensuite tu rentres dans 2 cas
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                val product = response.body()?.product
            // si c'est success on recupere response.body
                println(product?.brands)

                State.value = MainViewModelState.SuccessOFF(product)
                //searchExtract(product?.brands)
                //ici tu vas dans le 2e call api avec le nom en param
//                Log.i(TAG, "onResponse: $product")
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
//                Log.e(TAG, "onFailure: ", t)
                //sinon tu vas dans le statut failure
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


//    fun setDescription(description: Description) {
//        Paper.book().write("description", description)
//    }


//    fun setProduct(product: Product) {
//        val currentList = (Paper.book().read("products", emptyList<Product>()) ?: emptyList()).toMutableList()
//        currentList.add(0, product)
//
//        Paper.book().write("products", currentList)
//    }



}