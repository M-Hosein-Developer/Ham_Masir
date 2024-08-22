package ir.androidcoder.domain.useCase.search

import ir.androidcoder.domain.entities.HomeEntity

interface SearchUsecase {

    //home location
    suspend fun insertHomeLocation(homeLocation : HomeEntity)
    suspend fun getHomeLocation() : HomeEntity

}