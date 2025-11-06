package com.example.scanner.product

import androidx.lifecycle.ViewModel
import com.example.scanner.Product
import com.example.scanner.sampleProduct
import io.paperdb.Paper
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
    fun getProduct(): List<Product>?{
        return Paper.book().read<List<Product>>("product", emptyList())
    }
}
