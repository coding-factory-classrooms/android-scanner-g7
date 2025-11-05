package com.example.scanner.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProductListScreen(vm: ProductViewModel = viewModel()) {

    val uiState by vm.uiState.collectAsState()

    // Executed only when the key param changes
    // Unit == only once at the beginning
    LaunchedEffect(Unit) {
        println("ProductListScreen: LaunchedEffect")
        vm.loadProduct()
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            //ProductScreenBody(uiState)
        }
    }
}