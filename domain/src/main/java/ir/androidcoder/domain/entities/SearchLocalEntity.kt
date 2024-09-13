package ir.androidcoder.domain.entities

data class SearchLocalEntity(

    val osm_id: Long?,
    val city: String?,
    val country: String?,
    val countrycode: String?,
    val name: String?,
    val osm_key: String?,
    val osm_type: String?,
    val osm_value: String?,
    val state: String?,
    val lat: Double?,
    val lng: Double

)
