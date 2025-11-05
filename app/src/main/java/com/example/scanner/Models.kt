package com.example.scanner

data class Product(
    val title : String,
    val image: String,
    val description: String,
    val bar_code: String
)

val sampleProduct = listOf(
    Product("1","2","3", "4")
)