package ir.androidcoder.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.IDN

@Entity
data class SearchEntity(

    @PrimaryKey(autoGenerate = true)
    val id : Int ,
    val latitude : Double,
    val longitude : Double,
    val name : String,
    val address : String,
    val city : String,
    val category : String,
    val phone : String,

)