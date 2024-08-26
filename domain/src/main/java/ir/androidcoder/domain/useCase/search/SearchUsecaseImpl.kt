package ir.androidcoder.domain.useCase.search

import ir.androidcoder.domain.entities.HomeEntity
import ir.androidcoder.domain.entities.WorkEntity
import ir.androidcoder.domain.repository.SearchRepository

class SearchUsecaseImpl(private val searchRepository: SearchRepository) : SearchUsecase {

    //home location
    override suspend fun insertHomeLocation(homeLocation: HomeEntity) = searchRepository.insertHomeLocation(homeLocation)

    override suspend fun getHomeLocation(): HomeEntity = searchRepository.getHomeLocation()

    //work location
    override suspend fun insertWorkLocation(workEntity: WorkEntity) = searchRepository.insertWorkLocation(workEntity)

    override suspend fun getWorkLocation(): WorkEntity = searchRepository.getWorkLocation()

}