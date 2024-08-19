package ir.androidcoder.data.local.entities

import androidx.room.Entity

@Entity
data class SearchEntity(

    val name : String,
    val address : String,
    val city : String,
    val category : String,
    val phone : String,

)
