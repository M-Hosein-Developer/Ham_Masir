package ir.androidcoder.hammasir.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.androidcoder.data.remote.ApiService
import ir.androidcoder.data.repository.GetRoadRepositoryImpl
import ir.androidcoder.domain.repository.GetRoadRepository

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {


    @Provides
    @Suppress
    fun provideGetRoadRepository(apiService: ApiService) : GetRoadRepository = GetRoadRepositoryImpl(apiService)


}