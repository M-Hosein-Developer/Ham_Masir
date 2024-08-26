package ir.androidcoder.data.mapper

import ir.androidcoder.domain.entities.HomeEntity
import ir.androidcoder.domain.entities.WorkEntity

//Home Mapper
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

//Work Mapper
fun WorkEntity.toWorkData() = ir.androidcoder.data.local.entities.WorkEntity(

    id = id,
    latitude = latitude,
    longitude = longitude,
    name = name,
    address = address,
    city = city

)

fun ir.androidcoder.data.local.entities.WorkEntity.toWorkDomain() = WorkEntity(

    id = id,
    latitude = latitude,
    longitude = longitude,
    name = name,
    address = address,
    city = city

)
