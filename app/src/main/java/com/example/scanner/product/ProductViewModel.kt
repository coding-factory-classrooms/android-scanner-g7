package com.example.scanner.product

import androidx.lifecycle.ViewModel
import com.example.scanner.Product
import com.example.scanner.sampleProduct
import kotlinx.coroutines.flow.MutableStateFlow

sealed class ProductListUiState {
    data object Loading : ProductListUiState()

    data class Success(val product: List<Product>) : ProductListUiState()

    data class Error(val message: String) : ProductListUiState()
}

class ProductViewModel : ViewModel() {

    val productFlow = MutableStateFlow(listOf<Product>())

    val uiState = MutableStateFlow<ProductListUiState>(ProductListUiState.Loading)

    fun loadProduct() {

        uiState.value = ProductListUiState.Loading

        uiState.value = ProductListUiState.Success(
            product = sampleProduct
        )

        productFlow.value = sampleProduct

    }
    fun getProductById(productId: String?): Product? {
        return uiState.value.let { state ->
            if (state is ProductListUiState.Success) {
                state.product.find { it.id == productId }
            } else null
        }
    }
}
