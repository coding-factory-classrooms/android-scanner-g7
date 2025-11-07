package com.example.scanner.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.scanner.Product
import com.example.scanner.ui.theme.ScannerTheme
import com.google.gson.Gson

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productJson = intent.getStringExtra("product_json")
        val product = productJson?.let { Gson().fromJson(it, Product::class.java) }

        setContent {
            ScannerTheme {
                product?.let {
                    ProductDetailScreen(product = it)
                }
            }
        }
    }
}
