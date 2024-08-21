package ir.androidcoder.domain.entities

data class SearchEntity(

    val id : Int ,
    val name : String,
    val latitude : Double,
    val longitude : Double,
    val address : String,
    val city : String,
    val category : String,
    val phone : String,

)
