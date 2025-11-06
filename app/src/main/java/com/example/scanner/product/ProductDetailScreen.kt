// ProductDetailScreen.kt
package com.example.scanner.product

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.scanner.Product

@Composable
fun ProductDetailScreen(product: Product) {
    Column(modifier = Modifier.padding(16.dp)) {
//        Button() { }
        Text("Produit: ${product.brands}", style = MaterialTheme.typography.titleLarge)
        Text("ID: ${product.id}")
        AsyncImage(
            model = product.imageFrontURL,
            contentDescription = "Product Image",
            modifier = Modifier.size(200.dp)
        )
        // ici tu peux ajouter plus de détails du produit
    }
}
