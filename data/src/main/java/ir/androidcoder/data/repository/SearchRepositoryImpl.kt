package ir.androidcoder.data.repository

import ir.androidcoder.data.local.MyDao
import ir.androidcoder.data.mapper.toHomeData
import ir.androidcoder.data.mapper.toHomeDomain
import ir.androidcoder.domain.entities.HomeEntity
import ir.androidcoder.domain.repository.SearchRepository

class SearchRepositoryImpl(private val dao: MyDao) : SearchRepository {

    //Home Location
    override suspend fun insertHomeLocation(homeLocation: HomeEntity) = dao.insertHomeItem(homeLocation.toHomeData())

    override suspend fun getHomeLocation(): HomeEntity = dao.getHomeItems().toHomeDomain()


}