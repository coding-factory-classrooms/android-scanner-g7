package com.example.scanner.home

import androidx.lifecycle.ViewModel
import com.example.scanner.OpenFoodFactApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

sealed class MainViewModelState {
    data object Loading : MainViewModelState()
    data class Success(val bottle: Product) : MainViewModelState()
    data class FailureScan(val message: String) : MainViewModelState()
    data class FailureBottle(val message: String) : MainViewModelState()

}
data class Product( val barcode: String)
class HomeViewModel : ViewModel() {
    val viewModel = HomeViewModel()

    private val State = MutableStateFlow<MainViewModelState>(MainViewModelState.Loading)
    val uiState = State.asStateFlow()

    private val DebugMode = MutableStateFlow(false)
    val isDebugMode = DebugMode.asStateFlow()


    val retrofit = Retrofit.Builder()
        .baseUrl("https://world.openfoodfacts.org/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(OpenFoodFactApi::class.java)

    findViewById<Button>(R.id.searchButton).setOnClickListener {
        viewModel.searchProduct("34567")
    }

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
}