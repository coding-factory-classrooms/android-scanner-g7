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
     fun addProduct(newProduct: Product) {
         val current = uiState.value
         if (current is ProductListUiState.Success) {
             val newList = current.product.toMutableList()
             newList.add(0, newProduct) // on le met en haut
             uiState.value = ProductListUiState.Success(newList)
         } else {
             // sinon on crée une liste avec juste ce produit
             uiState.value = ProductListUiState.Success(listOf(newProduct))
         }
    }
}
