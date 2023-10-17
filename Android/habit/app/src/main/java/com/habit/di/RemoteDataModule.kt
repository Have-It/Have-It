package com.habit.di

import com.habit.data.api.MainService
import com.habit.data.repository.datasource.RemoteDataSource
import com.habit.data.repository.datasource.RemoteUserDataSource
import com.habit.data.repository.datasourceImpl.RemoteDataSourceImpl
import com.habit.data.repository.datasourceImpl.RemoteUserDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {
    @Provides
    fun provideMoviesRemoteDataSource(apiService: MainService) : RemoteDataSource =
        RemoteDataSourceImpl(apiService)

    @Provides
    fun provideRemoteUserDataSource(apiService: MainService) : RemoteUserDataSource =
        RemoteUserDataSourceImpl(apiService)
}