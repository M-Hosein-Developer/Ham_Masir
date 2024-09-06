package ir.androidcoder.hammasir.util

import androidx.compose.runtime.mutableStateOf
import ir.androidcoder.domain.entities.RouteEntity

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