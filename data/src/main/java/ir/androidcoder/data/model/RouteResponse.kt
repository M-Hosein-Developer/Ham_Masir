package ir.androidcoder.data.model

data class RouteResponse(
    val info: Info,
    val paths: List<Path>
) {

    data class Info(
        val copyrights: List<String>,
        val road_data_timestamp: String,
        val took: Int
    )

    data class Path(
        val ascend: Double,
        val bbox: List<Double>,
        val descend: Double,
        val details: Details,
        val distance: Double,
        val instructions: List<Instruction>,
        val legs: List<Any>,
        val points: String,
        val points_encoded: Boolean,
        val points_encoded_multiplier: Double,
        val snapped_waypoints: String,
        val time: Int,
        val transfers: Int,
        val weight: Double
    ) {
        class Details

        data class Instruction(
            val distance: Double,
            val exit_number: Int,
            val exited: Boolean,
            val heading: Double,
            val interval: List<Int>,
            val last_heading: Double,
            val motorway_junction: String?,
            val sign: Int,
            val street_destination: String?,
            val street_destination_ref: String?,
            val street_name: String?,
            val street_ref: String?,
            val text: String?,
            val time: Int?,
            val turn_angle: Double?
        )
    }
}