package ir.androidcoder.domain.useCase.road

import ir.androidcoder.domain.entities.RouteEntity
import ir.androidcoder.domain.repository.GetRoadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RoadUsecaseImpl(private val getRoadRepository: GetRoadRepository) : RoadUsecase {


    override suspend fun getRoute(point1: String, point2: String): Flow<RouteEntity> = flow {

        getRoadRepository.getRoute(point1 , point2).collect{
            emit(it)
        }

    }


}