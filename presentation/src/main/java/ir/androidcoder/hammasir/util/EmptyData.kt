package ir.androidcoder.hammasir.util

import androidx.compose.runtime.mutableStateOf
import ir.androidcoder.domain.entities.RouteEntity
import ir.androidcoder.domain.entities.SearchEntity

val locationDataEmpty = mutableStateOf(
    RouteEntity(
        info = RouteEntity.Info(
            copyrights = listOf("© OpenStreetMap contributors", "© MapTiler"),
            road_data_timestamp = "2024-08-30T12:34:56Z",
            took = 1500
        ),
        paths = listOf(
            RouteEntity.Path(
                ascend = 120.0,
                bbox = listOf(12.34, 56.78, 23.45, 67.89),
                descend = 100.0,
                details = RouteEntity.Path.Details(),
                distance = 5000.0,
                instructions = listOf(
                    RouteEntity.Path.Instruction(
                        distance = 100.0,
                        exit_number = 1,
                        exited = true,
                        heading = 90.0,
                        interval = listOf(0, 10),
                        last_heading = 85.0,
                        motorway_junction = "A1",
                        sign = 2,
                        street_destination = "Downtown",
                        street_destination_ref = "DTN",
                        street_name = "Main St",
                        street_ref = "M1",
                        text = "Turn right onto Main St",
                        time = 120,
                        turn_angle = 90.0
                    )
                ),
                legs = emptyList(),
                points = "encodedPolylineString",
                points_encoded = true,
                points_encoded_multiplier = 1.0,
                snapped_waypoints = "snappedPolylineString",
                time = 3600,
                transfers = 0,
                weight = 1.0
            )
        )
    )
)

val fakeSearchEntity = SearchEntity(
    hits = listOf(
        SearchEntity.Hit(
            city = "Tehran",
            country = "Iran",
            countrycode = "IR",
            extent = listOf(51.25, 35.50, 51.55, 35.90),
            name = "Tehran",
            osm_id = 123456,
            osm_key = "place",
            osm_type = "node",
            osm_value = "city",
            point = SearchEntity.Hit.Point(
                lat = 35.6892,
                lng = 51.3890
            ),
            state = "Tehran"
        ),
        SearchEntity.Hit(
            city = "Paris",
            country = "France",
            countrycode = "FR",
            extent = listOf(2.2242, 48.8156, 2.4699, 48.9022),
            name = "Paris",
            osm_id = 789012,
            osm_key = "place",
            osm_type = "node",
            osm_value = "city",
            point = SearchEntity.Hit.Point(
                lat = 48.8566,
                lng = 2.3522
            ),
            state = "Île-de-France"
        )
    ),
    locale = "en_US"
)

