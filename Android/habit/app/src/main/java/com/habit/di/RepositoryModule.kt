package com.habit.di

import com.habit.data.repository.HealthConnectRepositoryImpl
import com.habit.data.repository.MainRepositoryImpl
import com.habit.data.repository.MissionRepositoryImpl
import com.habit.data.repository.UserRepositoryImpl
import com.habit.data.repository.datasource.HealthConnectDataSource
import com.habit.data.repository.datasource.LocalDataSource
import com.habit.data.repository.datasource.PreferenceDataSource
import com.habit.data.repository.datasource.RemoteDataSource
import com.habit.data.repository.datasource.RemoteUserDataSource
import com.habit.domain.repository.HealthConnectRepository
import com.habit.domain.repository.MainRepository
import com.habit.domain.repository.MissionRepository
import com.habit.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMoviesRepository(
        remoteDataSource: RemoteDataSource,
//        localDataSource: LocalDataSource
    ): MainRepository =
        MainRepositoryImpl(remoteDataSource)

    @Provides
    fun provideUserRepository(
        preferenceDataSource: PreferenceDataSource,
        remoteDataSource: RemoteUserDataSource
    ): UserRepository =
        UserRepositoryImpl(preferenceDataSource, remoteDataSource)

    @Provides
    fun provideHealthConnectRepository(
        healthConnectDataSource: HealthConnectDataSource
    ): HealthConnectRepository =
        HealthConnectRepositoryImpl(healthConnectDataSource)

    @Provides
    fun provideMissionRepository(
        remoteDataSource: RemoteUserDataSource,
        localDataSource: LocalDataSource
    ): MissionRepository =
        MissionRepositoryImpl(remoteDataSource, localDataSource)
}