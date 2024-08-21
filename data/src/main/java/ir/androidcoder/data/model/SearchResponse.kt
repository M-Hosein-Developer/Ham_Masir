package ir.androidcoder.data.model

data class SearchResponse(

    val id : Int ,
    val name : String,
    val latitude : Double,
    val longitude : Double,
    val address : String,
    val city : String,
    val category : String,
    val phone : String,

)
