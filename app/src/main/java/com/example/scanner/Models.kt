package com.example.scanner
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

//data class Product(
//    val title : String,
//    val image: String,
//    val description: String,
//    val bar_code: String
//)

val sampleProduct = listOf(
    Product("1","volvic","https://www.madeinchampeyroux.com/98-large_default/bouteille-d-eau.jpg"),
    Product("2","oasis","https://m.media-amazon.com/images/I/71cTmcXJ2+L._AC_SL1500_.jpg"),
    Product("3","ice tea","https://m.media-amazon.com/images/I/71cTmcXJ2+L._AC_SL1500_.jpg"),


    Product("4","Coca-cola","https://images.openfoodfacts.org/images/products/301/762/042/2003/front_en.633.200.jpg")

)


//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////// OpenFoodfact Call API //////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
@Serializable
data class Product (
    @SerializedName("_id")
    val id: String,
    // ca c est le titre
    val brands: String,
    @SerializedName("image_front_url")
    val imageFrontURL: String

)


data class ProductResponse(
    val code: String,
    val status: Int,
    val product: Product
)



//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////// WikiMedia Call API //////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////



data class Description (
    val title: String,
    val extract: String,
)
