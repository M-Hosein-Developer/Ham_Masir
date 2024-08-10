package ir.androidcoder.domain.useCase.road

import ir.androidcoder.domain.entities.RouteEntity
import kotlinx.coroutines.flow.Flow

interface RoadUsecase {

    suspend fun getRoute(point1: String, point2: String): Flow<RouteEntity>



}