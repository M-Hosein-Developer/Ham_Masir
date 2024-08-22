package ir.androidcoder.hammasir.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.androidcoder.domain.repository.GetRoadRepository
import ir.androidcoder.domain.repository.SearchRepository
import ir.androidcoder.domain.useCase.road.RoadUsecase
import ir.androidcoder.domain.useCase.road.RoadUsecaseImpl
import ir.androidcoder.domain.useCase.search.SearchUsecase
import ir.androidcoder.domain.useCase.search.SearchUsecaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UsecaseModule {


    @Provides
    @Singleton
    fun provideRoadUsecase(getRoadRepository: GetRoadRepository): RoadUsecase = RoadUsecaseImpl(getRoadRepository)

    @Provides
    @Singleton
    fun provideSearchUsecase(searchRepository: SearchRepository): SearchUsecase = SearchUsecaseImpl(searchRepository)


}