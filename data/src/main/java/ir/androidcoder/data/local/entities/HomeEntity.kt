package ir.androidcoder.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HomeEntity(

    @PrimaryKey
    val id : Int ,
    val latitude : Double,
    val longitude : Double,
    val name : String,
    val address : String,
    val city : String,

)
