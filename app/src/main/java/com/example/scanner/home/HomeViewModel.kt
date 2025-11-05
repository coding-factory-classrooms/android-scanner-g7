package com.example.scanner.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed class MainViewModelState {
    data object Loading : MainViewModelState()
    data class Success(val bottle: Product) : MainViewModelState()
    data class FailureScan(val message: String) : MainViewModelState()
    data class FailureBottle(val message: String) : MainViewModelState()

}
data class Product( val barcode: String)
class HomeViewModel : ViewModel() {

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
}