package ir.androidcoder.data.mapper

import ir.androidcoder.domain.entities.HomeEntity

fun HomeEntity.toHomeData() = ir.androidcoder.data.local.entities.HomeEntity(

    id = id,
    latitude = latitude,
    longitude = longitude,
    name = name,
    address = address,
    city = city

)

fun ir.androidcoder.data.local.entities.HomeEntity.toHomeDomain() = HomeEntity(

    id = id,
    latitude = latitude,
    longitude = longitude,
    name = name,
    address = address,
    city = city

)