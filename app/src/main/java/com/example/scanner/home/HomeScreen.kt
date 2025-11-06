package com.example.scanner.home

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scanner.OpenFoodFactApi
import com.example.scanner.Product
import com.example.scanner.R
import com.example.scanner.product.ProductListActivity
import com.example.scanner.WikipediaApi
import com.google.gson.Gson
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val client = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .header("User-Agent", "MonApp/1.0 (contact@monemail.com)")
            .build()
        chain.proceed(newRequest)
    }
    .build()

// premier call api sur cette url
val retrofitOFF = Retrofit.Builder()
    .baseUrl("https://world.openfoodfacts.org/api/v2/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
// 2e call
val retrofitWM = Retrofit.Builder()
    .baseUrl("https://en.wikipedia.org/api/rest_v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()


val apiOFF = retrofitOFF.create(OpenFoodFactApi::class.java)

val apiWM = retrofitWM.create(WikipediaApi::class.java)


@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel {
    HomeViewModel(apiOFF, apiWM)
}) {
    val context = LocalContext.current
    val uiState by homeViewModel.uiState.collectAsState()
    val isDebugMode by homeViewModel.isDebugMode.collectAsState()

    val barcodeLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
        homeViewModel.searchProduct(result.contents)
    }
    // le result c est le bar_code

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { homeViewModel.toggleDebugMode() }
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Button(onClick = {
            if (isDebugMode) {
                homeViewModel.searchProduct("54491472")

            } else {
                val options = ScanOptions()
                options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
                options.setPrompt("Scan a barcode")
                options.setBeepEnabled(false)
                options.setBarcodeImageEnabled(true)
                barcodeLauncher.launch(options)
            }
        }) {
            Text("Start a scan")
        }

        Spacer(modifier = Modifier.padding(8.dp))

        if (isDebugMode) {
            Text("Debug mode")
        }

        when (val state = uiState) {
            is MainViewModelState.Loading -> {
            }

            is MainViewModelState.SuccessOFF -> {
                val gson = Gson()
                val productGson = gson.toJson(state.product)
                val intent = Intent(context, ProductListActivity::class.java)
                intent.putExtra("product_json",productGson)
                context.startActivity(intent)
            }

            is MainViewModelState.FailureScan -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }

            is MainViewModelState.FailureBottle -> {
                Text(state.message)
            }

            is MainViewModelState.SuccessWM ->
                Text("Extract: ${state.extract}")
        }
    }
}