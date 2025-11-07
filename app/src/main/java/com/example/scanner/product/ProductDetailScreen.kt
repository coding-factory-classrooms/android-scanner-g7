package com.example.scanner.product


import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scanner.Product
import com.example.scanner.WikipediaApi
import com.example.scanner.home.MainActivity
import com.example.scanner.home.client
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 2e call
val retrofitWM = Retrofit.Builder()
    .baseUrl("https://en.wikipedia.org/api/rest_v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

val apiWM = retrofitWM.create(WikipediaApi::class.java)


@Composable
fun ProductDetailScreen(vm: ProductDetailViewModel = viewModel() { ProductDetailViewModel(apiWM) }, product: Product) {
    val context = LocalContext.current

    val description by vm.extractState.collectAsState()

    LaunchedEffect(product.brands) {
        vm.searchExtract(product.brands)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                (context as Activity).finish()
//                val intent = Intent(context, ProductDetailActivity::class.java)
//                context.startActivity(intent)
//                (context as Activity).finish()
            }
        ) {
            Text("<- Return")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Image du produit
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.imageFrontURL)
                .crossfade(true)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .build(),
            contentDescription = "Product image",
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(10.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Infos du produit
        Text(text = "Nom: ${product.brands}", style = MaterialTheme.typography.titleMedium)
        Text(text = "ID: ${product.id}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Description: ${description ?: "Aucune données sur ce produit dans notre API de merde"}", style = MaterialTheme.typography.bodyMedium)    }
}
