package ir.androidcoder.data.repository

import ir.androidcoder.data.mapper.mapToEntity
import ir.androidcoder.data.remote.ApiService
import ir.androidcoder.domain.entities.RouteEntity
import ir.androidcoder.domain.repository.GetRoadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRoadRepositoryImpl(private val apiService: ApiService) : GetRoadRepository {


    override suspend fun getRoute(point1: String, point2: String): Flow<RouteEntity> = flow {


            val response = apiService.getRoute(key = "c5178a7a-f816-43d3-a1ba-d7434c8670f7" , point1 = point1 , point2 = point2)
            emit(mapToEntity(response))

    }


}