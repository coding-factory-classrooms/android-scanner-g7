package com.example.scanner.product

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.scanner.sampleProduct
import com.example.scanner.Product

import androidx.compose.foundation.lazy.items


import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scanner.home.MainActivity
import com.example.scanner.ui.theme.ScannerTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest


@Composable
fun ProductListScreen(
    vm: ProductViewModel = viewModel(),
    onProductClick: (Product) -> Unit,
  newProduct: Product? = null
) {

    val uiState by vm.uiState.collectAsState()

    // ⚡ Charger les produits une seule fois au lancement
    LaunchedEffect(Unit) {

        println("ProductListScreen: LaunchedEffect")
        vm.loadProduct()
        if (newProduct != null) {
            vm.addProduct(newProduct)
        }

    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            ProductScreenBody(uiState, onProductClick)
        }
    }
}


@Composable
fun ProductScreenBody(uiState: ProductListUiState, onProductClick: (Product) -> Unit) {
    when (uiState) {
        is ProductListUiState.Loading -> { /* Loading UI */ }
        is ProductListUiState.Success -> {
            ProductList(products = uiState.product, onProductClick = onProductClick)
        }
        is ProductListUiState.Error -> { /* Error UI */ }
    }
}


@Composable
fun ProductList(products: List<Product>, onProductClick: (Product) -> Unit) {
    LazyColumn {
        items(products) { product ->
            ProductCard(product = product, onProductClick = onProductClick)
        }
    }
}


//@PreviewFontScale
@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    ScannerTheme {
        ProductCard(
            product = sampleProduct.first(),
            onProductClick = {}
        )
    }
}



@Composable
fun ProductCard(product: Product, onProductClick: (Product) -> Unit) {
    Row(
        modifier = Modifier.padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = product.imageFrontURL,
            contentDescription = "Product image",
            modifier = Modifier.size(50.dp).clip(RoundedCornerShape(10.dp))
        )

        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = product.brands, style = MaterialTheme.typography.titleSmall)
            Text(text = product.id, style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = { onProductClick(product) }) {
            Text("voir")
        }
    }
}


class StatePreviewProvider : PreviewParameterProvider<ProductListUiState> {
    override val values: Sequence<ProductListUiState>
        get() = sequenceOf(
            ProductListUiState.Loading,
            ProductListUiState.Success(sampleProduct),
            ProductListUiState.Error("Probleme de connexion internet"),
        )
}

@Preview
@Composable
fun ProductBodyPreview(
    @PreviewParameter(StatePreviewProvider::class) uiState: ProductListUiState,
) {
    ScannerTheme() {
        Surface {
            ProductScreenBody(uiState, onProductClick = {})
        }
    }
}
