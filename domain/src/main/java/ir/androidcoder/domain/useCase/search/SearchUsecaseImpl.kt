package ir.androidcoder.domain.useCase.search

import ir.androidcoder.domain.entities.HomeEntity
import ir.androidcoder.domain.entities.SearchEntity
import ir.androidcoder.domain.entities.SearchLocalEntity
import ir.androidcoder.domain.entities.WorkEntity
import ir.androidcoder.domain.repository.SearchRepository

class SearchUsecaseImpl(private val searchRepository: SearchRepository) : SearchUsecase {

    //home location
    override suspend fun insertHomeLocation(homeLocation: HomeEntity) = searchRepository.insertHomeLocation(homeLocation)

    override suspend fun getHomeLocation(): HomeEntity = searchRepository.getHomeLocation()

    //work location
    override suspend fun insertWorkLocation(workEntity: WorkEntity) = searchRepository.insertWorkLocation(workEntity)

    override suspend fun getWorkLocation(): WorkEntity = searchRepository.getWorkLocation()

    //search location
    override suspend fun getSearchLocation(search: String, language: String): SearchEntity = searchRepository.getSearchLocation(search , language)

    //search history
    override suspend fun insertSearchHistory(searchHistory: SearchLocalEntity) = searchRepository.insertSearchHistory(searchHistory)

    override suspend fun getSearchHistory(): List<SearchLocalEntity> = searchRepository.getSearchHistory()


}