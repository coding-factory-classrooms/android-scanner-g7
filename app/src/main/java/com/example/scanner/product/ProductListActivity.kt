package com.example.scanner.product

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.scanner.Product
import com.example.scanner.ui.theme.ScannerTheme
import com.google.gson.Gson

class ProductListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gson = Gson()
        val productJson = intent.getStringExtra("product_json")
        val productObject: Product? = productJson?.let {
            try {
                gson.fromJson(it, Product::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        setContent {
            ScannerTheme {
                ProductListScreen(newProduct = productObject)
            }
        }
    }
}
