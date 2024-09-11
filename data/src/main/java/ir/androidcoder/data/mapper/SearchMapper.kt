package ir.androidcoder.data.mapper

import ir.androidcoder.data.model.SearchResponse
import ir.androidcoder.domain.entities.HomeEntity
import ir.androidcoder.domain.entities.SearchEntity
import ir.androidcoder.domain.entities.SearchLocalEntity
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
fun SearchResponse.toSearchEntity(): SearchEntity {
    return SearchEntity(
        hits = this.hits?.map { hit ->
            SearchEntity.Hit(
                city = hit.city,
                country = hit.country,
                countrycode = hit.countrycode,
                extent = hit.extent?.toList(),
                name = hit.name,
                osm_id = hit.osm_id,
                osm_key = hit.osm_key,
                osm_type = hit.osm_type,
                osm_value = hit.osm_value,
                point = SearchEntity.Hit.Point(
                    lat = hit.point?.lat,
                    lng = hit.point?.lng
                ),
                state = hit.state
            )
        },
        locale = this.locale
    )
}

fun List<ir.androidcoder.data.local.entities.SearchEntity>.toSearchEntityDomainList(): List<SearchLocalEntity> = this.map { searchEntity ->
    SearchLocalEntity(
        osm_id = searchEntity.osm_id,
        city = searchEntity.city,
        country = searchEntity.country,
        countrycode = searchEntity.countrycode,
        extent = searchEntity.extent,
        name = searchEntity.name,
        osm_key = searchEntity.osm_key,
        osm_type = searchEntity.osm_type,
        osm_value = searchEntity.osm_value,
        state = searchEntity.state,
        lat = searchEntity.lat,
        lng = searchEntity.lng
    )
}

fun SearchLocalEntity.toSearchEntityData(): ir.androidcoder.data.local.entities.SearchEntity =
    ir.androidcoder.data.local.entities.SearchEntity(
        osm_id = this.osm_id,
        city = this.city,
        country = this.country,
        countrycode = this.countrycode,
        extent = this.extent,
        name = this.name,
        osm_key = this.osm_key,
        osm_type = this.osm_type,
        osm_value = this.osm_value,
        state = this.state,
        lat = this.lat,
        lng = this.lng
    )