package com.habit.di

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import com.habit.data.repository.datasource.HealthConnectDataSource
import com.habit.data.repository.datasourceImpl.HealthConnectDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HealthConnectModule {

    @Provides
    @Singleton
    fun provideHealthConnectClient(@ApplicationContext context: Context): HealthConnectClient =
        HealthConnectClient.getOrCreate(context)

    @Provides
    fun provideHealthConnectDataSource(healthConnectClient: HealthConnectClient): HealthConnectDataSource =
        HealthConnectDataSourceImpl(healthConnectClient)
}