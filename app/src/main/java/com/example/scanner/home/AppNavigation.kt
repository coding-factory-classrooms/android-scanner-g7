package com.example.scanner.product

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scanner.Product
import com.example.scanner.home.HomeScreen

@Composable
fun AppNavigation(products: List<Product>) {
    val navController = rememberNavController()
    val vm: ProductViewModel = viewModel()


    NavHost(navController = navController, startDestination = "home") { //la on dit que notre première view c'est home
        composable("home") {
            HomeScreen(navController = navController) // home qui renvoi vers HomeScreen
        }

        composable("productList") {
            ProductListScreen(vm = vm) { clickedProduct ->
                navController.navigate("productDetail/${clickedProduct.id}") //pareil route qui envoie vers la liste et on passe le parametre id si on clic sur voir un product
            }
        }

        composable("productDetail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") //on recupère la valeur passé depuis la productlist (l'id en gros)

            val product = vm.getProductById(productId) // on appelle getproductbyid qui est dans le product view model pour recup les infos du produits

            product?.let {
                ProductDetailScreen(product = it) // et on envoie vers la vu avec les infos du produit
            }
        }
    }
}
