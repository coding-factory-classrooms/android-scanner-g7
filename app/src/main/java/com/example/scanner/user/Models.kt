package com.example.scanner.user

import android.media.Image

data class Product(
    val title : String,
    val image: String,
    val description: String,
)

val sampleProduct = listOf(
    Product("1","2","3")
)