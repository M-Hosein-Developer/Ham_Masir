package ir.androidcoder.data.mapper

import ir.androidcoder.data.model.RouteResponse
import ir.androidcoder.domain.entities.RouteEntity

fun mapToEntity(routeResponse: RouteResponse): RouteEntity =
    RouteEntity(info = routeResponse.info.toEntity(),
        paths = routeResponse.paths.map { it.toEntity() })


private fun RouteResponse.Info.toEntity(): RouteEntity.Info = RouteEntity.Info(
    copyrights = this.copyrights, road_data_timestamp = this.road_data_timestamp, took = this.took
)


private fun RouteResponse.Path.toEntity(): RouteEntity.Path = RouteEntity.Path(
    ascend = this.ascend,
    bbox = this.bbox,
    descend = this.descend,
    details = RouteEntity.Path.Details(), // فرض بر این است که کلاس Details نیازی به نگاشت ندارد
    distance = this.distance,
    instructions = this.instructions.map { it.toEntity() },
    legs = this.legs, // فرض بر این است که نیازی به نگاشت برای legs نیست
    points = this.points,
    points_encoded = this.points_encoded,
    points_encoded_multiplier = this.points_encoded_multiplier,
    snapped_waypoints = this.snapped_waypoints,
    time = this.time,
    transfers = this.transfers,
    weight = this.weight
)

private fun RouteResponse.Path.Instruction.toEntity(): RouteEntity.Path.Instruction =
    RouteEntity.Path.Instruction(
        distance = this.distance,
        exit_number = this.exit_number,
        exited = this.exited,
        heading = this.heading,
        interval = this.interval,
        last_heading = this.last_heading,
        motorway_junction = this.motorway_junction,
        sign = this.sign,
        street_destination = this.street_destination,
        street_destination_ref = this.street_destination_ref,
        street_name = this.street_name,
        street_ref = this.street_ref,
        text = this.text,
        time = this.time,
        turn_angle = this.turn_angle
    )
