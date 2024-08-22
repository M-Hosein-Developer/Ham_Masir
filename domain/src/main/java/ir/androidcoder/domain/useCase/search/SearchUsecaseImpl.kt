package ir.androidcoder.domain.useCase.search

import ir.androidcoder.domain.entities.HomeEntity
import ir.androidcoder.domain.repository.SearchRepository

class SearchUsecaseImpl(private val searchRepository: SearchRepository) : SearchUsecase {

    override suspend fun insertHomeLocation(homeLocation: HomeEntity) = searchRepository.insertHomeLocation(homeLocation)

    override suspend fun getHomeLocation(): HomeEntity = searchRepository.getHomeLocation()

}