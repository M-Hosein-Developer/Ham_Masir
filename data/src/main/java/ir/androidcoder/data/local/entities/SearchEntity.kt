package ir.androidcoder.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchEntity(

    @PrimaryKey(autoGenerate = false)
    val osm_id: Long?,
    val city: String?,
    val country: String?,
    val countrycode: String?,
    val extent: List<Double>?,
    val name: String?,
    val osm_key: String?,
    val osm_type: String?,
    val osm_value: String?,
    val state: String?,
    val lat: Double?,
    val lng: Double

)
