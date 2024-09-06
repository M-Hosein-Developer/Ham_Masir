package ir.androidcoder.domain.repository

import ir.androidcoder.domain.entities.HomeEntity
import ir.androidcoder.domain.entities.SearchEntity
import ir.androidcoder.domain.entities.WorkEntity

interface SearchRepository {

    //home location
    suspend fun insertHomeLocation(homeLocation : HomeEntity)
    suspend fun getHomeLocation() : HomeEntity

    //work location
    suspend fun insertWorkLocation(workLocation : WorkEntity)
    suspend fun getWorkLocation() : WorkEntity

    //search location
    suspend fun getSearchLocation(search : String , language : String) : SearchEntity
}