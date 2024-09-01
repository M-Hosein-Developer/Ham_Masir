package ir.androidcoder.data.mapper

import ir.androidcoder.domain.entities.HomeEntity
import ir.androidcoder.domain.entities.SearchEntity
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

//Search Mapper
fun SearchEntity.toSearchData() = ir.androidcoder.data.local.entities.SearchEntity(

    id = id,
    latitude = latitude,
    longitude = longitude,
    name = name,
    address = address,
    category = category,
    city = city,
    phone = phone

)

fun List<ir.androidcoder.data.local.entities.SearchEntity>.toSearchDomainList(): List<SearchEntity> =
    this.map { searchEntity ->
        SearchEntity(
            id = searchEntity.id,
            latitude = searchEntity.latitude,
            longitude = searchEntity.longitude,
            name = searchEntity.name,
            address = searchEntity.address,
            category = searchEntity.category,
            city = searchEntity.city,
            phone = searchEntity.phone
        )
    }
