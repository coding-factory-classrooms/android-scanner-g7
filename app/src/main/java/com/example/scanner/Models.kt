package com.example.scanner
import com.google.gson.annotations.SerializedName
import java.net.URL

data class Product(
    val title : String,
    val image: String,
    val description: String,
    val bar_code: String
)

val sampleProduct = listOf(
    Product("volvic","https://www.madeinchampeyroux.com/98-large_default/bouteille-d-eau.jpg","3", "1"),
    Product("oasis","https://m.media-amazon.com/images/I/71cTmcXJ2+L._AC_SL1500_.jpg","3", "2"),
    Product("ice tea","https://m.media-amazon.com/images/I/71cTmcXJ2+L._AC_SL1500_.jpg","3", "3"),


    Product("Coca-cola","https://images.openfoodfacts.org/images/products/301/762/042/2003/front_en.633.200.jpg","4", "4")
)


//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////// OpenFoodfact Call API //////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

data class InfoProduct (
    val id: String,
    @SerializedName("product_name_fr")
    val productNameFr: String,
    @SerializedName("image_front_url")
    val imageFrontURL: String
)



//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////// WikiMedia Call API //////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////


data class Page (
    val title: String,
    val extract: String
)
