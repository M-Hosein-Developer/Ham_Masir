package ir.androidcoder.domain.repository

import ir.androidcoder.domain.entities.HomeEntity

interface SearchRepository {

    //home location
    suspend fun insertHomeLocation(homeLocation : HomeEntity)
    suspend fun getHomeLocation() : HomeEntity


}