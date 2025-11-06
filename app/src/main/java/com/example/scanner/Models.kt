package com.example.scanner
import com.google.gson.annotations.SerializedName

//data class Product(
//    val title : String,
//    val image: String,
//    val description: String,
//    val bar_code: String
//)

val sampleProduct = listOf(
    Product("volvic","https://www.madeinchampeyroux.com/98-large_default/bouteille-d-eau.jpg","3", "1"),
    Product("oasis","https://m.media-amazon.com/images/I/71cTmcXJ2+L._AC_SL1500_.jpg","3", "2"),
    Product("ice tea","https://m.media-amazon.com/images/I/71cTmcXJ2+L._AC_SL1500_.jpg","3", "3"),


    Product("Coca-cola","https://images.openfoodfacts.org/images/products/301/762/042/2003/front_en.633.200.jpg","4", "4")

)


//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////// OpenFoodfact Call API //////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////


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



data class Welcome (
    val type: String,
    val title: String,
    val displaytitle: String,
    val wikibaseItem: String,
    val pageid: Long,
    val lang: String,
    val dir: String,
    val revision: String,
    val tid: String,
    val timestamp: String,
    val description: String,
    val descriptionSource: String,
    // c est la description
    val extract: String,
    val extractHTML: String
)
