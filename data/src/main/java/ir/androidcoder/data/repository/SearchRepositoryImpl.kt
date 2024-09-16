package ir.androidcoder.data.repository

import ir.androidcoder.data.local.MyDao
import ir.androidcoder.data.mapper.toHomeData
import ir.androidcoder.data.mapper.toHomeDomain
import ir.androidcoder.data.mapper.toSearchEntity
import ir.androidcoder.data.mapper.toSearchEntityData
import ir.androidcoder.data.mapper.toSearchEntityDomainList
import ir.androidcoder.data.mapper.toWorkData
import ir.androidcoder.data.mapper.toWorkDomain
import ir.androidcoder.data.remote.ApiService
import ir.androidcoder.domain.entities.HomeEntity
import ir.androidcoder.domain.entities.SearchEntity
import ir.androidcoder.domain.entities.SearchLocalEntity
import ir.androidcoder.domain.entities.WorkEntity
import ir.androidcoder.domain.repository.SearchRepository

class SearchRepositoryImpl(private val dao: MyDao , private val apiService: ApiService) : SearchRepository {

    //Home Location
    override suspend fun insertHomeLocation(homeLocation: HomeEntity) = dao.insertHomeItem(homeLocation.toHomeData())

    override suspend fun getHomeLocation(): HomeEntity = dao.getHomeItems()?.toHomeDomain() ?: HomeEntity(0 , 0.0 , 0.0 , "" , "" , "")

    //Work Location
    override suspend fun insertWorkLocation(workLocation: WorkEntity) = dao.insertWorkItem(workLocation.toWorkData())

    override suspend fun getWorkLocation(): WorkEntity = dao.getWorkItem()?.toWorkDomain() ?: WorkEntity(0 , 0.0 , 0.0 , "" , "" , "")

    //Search Location
    override suspend fun getSearchLocation(search : String , language : String): SearchEntity = apiService.getGeocode(search , language , "c5178a7a-f816-43d3-a1ba-d7434c8670f7").toSearchEntity()

    //Search Location History
    override suspend fun insertSearchHistory(searchHistory: SearchLocalEntity) = dao.insertSearchItem(searchHistory.toSearchEntityData())

    override suspend fun getSearchHistory(): List<SearchLocalEntity> = dao.getSearchItems().toSearchEntityDomainList()

    override suspend fun deleteSearchHistory(id: Long) = dao.deleteSearchItem(id)


}