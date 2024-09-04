package ir.androidcoder.data.mapper

import ir.androidcoder.data.model.SearchResponse
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
fun SearchResponse.toSearchEntity(): SearchEntity {
    return SearchEntity(
        hits = this.hits.map { hit ->
            SearchEntity.Hit(
                city = hit.city,
                country = hit.country,
                countrycode = hit.countrycode,
                extent = hit.extent.toList(),
                name = hit.name,
                osm_id = hit.osm_id,
                osm_key = hit.osm_key,
                osm_type = hit.osm_type,
                osm_value = hit.osm_value,
                point = SearchEntity.Hit.Point(
                    lat = hit.point.lat,
                    lng = hit.point.lng
                ),
                state = hit.state
            )
        },
        locale = this.locale
    )
}

fun SearchEntity.toSearchResponse(): SearchResponse {
    return SearchResponse(
        hits = this.hits.map { hit ->
            SearchResponse.Hit(
                city = hit.city,
                country = hit.country,
                countrycode = hit.countrycode,
                extent = hit.extent.toList(),
                name = hit.name,
                osm_id = hit.osm_id,
                osm_key = hit.osm_key,
                osm_type = hit.osm_type,
                osm_value = hit.osm_value,
                point = SearchResponse.Hit.Point(
                    lat = hit.point.lat,
                    lng = hit.point.lng
                ),
                state = hit.state
            )
        },
        locale = this.locale
    )
}
