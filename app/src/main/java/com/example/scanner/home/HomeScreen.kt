package com.example.scanner.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scanner.R
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    val uiState by homeViewModel.uiState.collectAsState()
    val isDebugMode by homeViewModel.isDebugMode.collectAsState()

    val barcodeLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
        homeViewModel.onScanResult(result.contents)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { homeViewModel.toggleDebugMode() }
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Button(onClick = {
            if (isDebugMode) {
                homeViewModel.onScanResult("1234567890")
            } else {
                val options = ScanOptions()
                options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
                options.setPrompt("Scan a barcode")
                options.setBeepEnabled(false)
                options.setBarcodeImageEnabled(true)
                barcodeLauncher.launch(options)
            }
        }) {
            Text("Start a scan")
        }

        Spacer(modifier = Modifier.padding(8.dp))

        if (isDebugMode) {
            Text("Debug mode")
        }

        when (val state = uiState) {
            is MainViewModelState.Loading -> {
            }
            is MainViewModelState.Success -> {
                Text("Scanned code: ${state.bottle.barcode}")
            }
            is MainViewModelState.FailureScan -> {
                Text(state.message)
            }
            is MainViewModelState.FailureBottle -> {
                Text(state.message)
            }
        }
    }
}