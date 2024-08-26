package ir.androidcoder.domain.useCase.search

import ir.androidcoder.domain.entities.HomeEntity
import ir.androidcoder.domain.entities.WorkEntity

interface SearchUsecase {

    //home location
    suspend fun insertHomeLocation(homeLocation : HomeEntity)
    suspend fun getHomeLocation() : HomeEntity

    //work location
    suspend fun insertWorkLocation(workEntity: WorkEntity)
    suspend fun getWorkLocation() : WorkEntity

}