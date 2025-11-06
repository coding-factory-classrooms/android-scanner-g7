package com.example.scanner
import com.google.gson.annotations.SerializedName

//data class Product(
//    val title : String,
//    val image: String,
//    val description: String,
//    val bar_code: String
//)

val sampleProduct = listOf(
    Product("1","2","https://fr.wikipedia.org/wiki/Wikip%C3%A9dia:Accueil_principal#/media/Fichier:20220702_Araschnia_levana_02.jpg")
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
