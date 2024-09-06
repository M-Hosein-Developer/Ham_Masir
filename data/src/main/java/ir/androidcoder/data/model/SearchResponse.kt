package ir.androidcoder.data.model

data class SearchResponse(
    val hits: List<Hit>?,
    val locale: String?
) {
    data class Hit(
        val city: String?,
        val country: String?,
        val countrycode: String?,
        val extent: List<Double>?,
        val name: String?,
        val osm_id: Long?,
        val osm_key: String?,
        val osm_type: String?,
        val osm_value: String?,
        val point: Point?,
        val state: String?
    ) {
        data class Point(
            val lat: Double?,
            val lng: Double?
        )
    }
}
