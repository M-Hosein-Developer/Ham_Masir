package ir.androidcoder.data.repository

import ir.androidcoder.data.local.MyDao
import ir.androidcoder.data.mapper.toHomeData
import ir.androidcoder.data.mapper.toHomeDomain
import ir.androidcoder.data.mapper.toWorkData
import ir.androidcoder.data.mapper.toWorkDomain
import ir.androidcoder.domain.entities.HomeEntity
import ir.androidcoder.domain.entities.WorkEntity
import ir.androidcoder.domain.repository.SearchRepository

class SearchRepositoryImpl(private val dao: MyDao) : SearchRepository {

    //Home Location
    override suspend fun insertHomeLocation(homeLocation: HomeEntity) = dao.insertHomeItem(homeLocation.toHomeData())

    override suspend fun getHomeLocation(): HomeEntity = dao.getHomeItems()?.toHomeDomain() ?: HomeEntity(0 , 0.0 , 0.0 , "" , "" , "")

    //Work Location
    override suspend fun insertWorkLocation(workLocation: WorkEntity) = dao.insertWorkItem(workLocation.toWorkData())

    override suspend fun getWorkLocation(): WorkEntity = dao.getWorkItem()?.toWorkDomain() ?: WorkEntity(0 , 0.0 , 0.0 , "" , "" , "")


}