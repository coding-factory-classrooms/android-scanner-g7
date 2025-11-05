package com.example.scanner.product

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scanner.home.MainActivity
import com.example.scanner.ui.theme.ScannerTheme


@Composable
fun ProductListScreen(vm: ProductViewModel = viewModel()) {

    val uiState by vm.uiState.collectAsState()
    val context = LocalContext.current
    // Executed only when the key param changes
    // Unit == only once at the beginning
    LaunchedEffect(Unit) {
        println("ProductListScreen: LaunchedEffect")
        vm.loadProduct()
    }



    Scaffold { innerPadding ->


        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }
                ) {
                    Text("<- Return")
                }

                Spacer(modifier = Modifier.width(16.dp))

                ProductScreenBody(uiState)
            }
        }
    }
}

@Composable
fun ProductScreenBody(uiState: ProductListUiState) {
    when (uiState) {
        is ProductListUiState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Text(text = "Loading...")
            }

        }

        is ProductListUiState.Success -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top
            ) {
                ProductList(products = uiState.product)
            }
        }

        is ProductListUiState.Error -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = uiState.message)
            }
        }
    }
}

@Composable
fun ProductList(products: List<Product>) {
    LazyColumn {
        items(products) { product ->
            ProductCard(product = product)
        }

    }
}

//@PreviewFontScale
@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    ScannerTheme {
        ProductCard(product = sampleProduct.first())
    }
}


@Composable
fun ProductCard(product: Product) {
    Row(
        modifier = Modifier.padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
//        Image(
//            painter = painterResource(id = movie.posterId),
//            contentDescription = "Movie poster",
//            modifier = Modifier
//                .size(50.dp)
//                .clip(RoundedCornerShape(10.dp))
//        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = product.title, style = MaterialTheme.typography.titleSmall)
            Text(text = product.bar_code, style = MaterialTheme.typography.bodyMedium)
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
            ProductScreenBody(uiState)
        }
    }
}
