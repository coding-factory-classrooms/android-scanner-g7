package com.example.scanner.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.journeyapps.barcodescanner.ScanContract

sealed class MainViewModelState {
    data object Loading : MainViewModelState()
    data class Success(val bottle: Product) : MainViewModelState()
    data class FailureScan(val message: String) : MainViewModelState()
    data class FailureBottle(val message: String) : MainViewModelState()

}

class HomeViewModel : ViewModel() {

    var scannedCode = mutableStateOf<String?>(null)

    @Composable
    fun getCodeBarre(): MutableState<String?> {
        val barcodeLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
            scannedCode = result.contents
        }
        return scannedCode
    }
}