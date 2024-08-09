package ir.androidcoder.domain.repository

import ir.androidcoder.domain.entities.RouteEntity
import kotlinx.coroutines.flow.Flow

interface GetRoadRepository {

    suspend fun getRoute(point1: String, point2: String): Flow<RouteEntity>

}