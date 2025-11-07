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
    fun addProductIfNotExists(newProduct: Product) {
        val currentList =
            (Paper.book().read("products", emptyList<Product>()) ?: emptyList()).toMutableList()

        val alreadyThere = currentList.any { it.id == newProduct.id }
        if (!alreadyThere) {
            currentList.add(0, newProduct)
            Paper.book().write("products", currentList)
            uiState.value = ProductListUiState.Success(currentList)
        }
    }


    fun loadProductsFromStorage() {
        val products = Paper.book().read("products", emptyList<Product>()) ?: emptyList()
        uiState.value = ProductListUiState.Success(products)
    }
//    fun addProduct(newProduct: Product) {
//        val currentList = (Paper.book().read("products", emptyList<Product>()) ?: emptyList()).toMutableList()
//        currentList.add(0, newProduct)
//        Paper.book().write("products", currentList)
//        uiState.value = ProductListUiState.Success(currentList)
//    }


    fun getProduct(): List<Product>?{
        return Paper.book().read("products", emptyList<Product>())
    }

    fun getProductSize(): Int {
        return getProduct() ?.size ?: 0
    }

    fun suppProduct(product: Product) {
        val currentList = (Paper.book().read("products", emptyList<Product>()) ?: emptyList()).toMutableList()
        currentList.remove(product)
        Paper.book().write("products", currentList)
        uiState.value = ProductListUiState.Success(currentList)

    }
}
