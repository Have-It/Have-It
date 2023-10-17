package com.habit.di

import android.content.Context
import com.habit.data.db.ExerciseDao
import com.habit.data.repository.datasource.LocalDataSource
import com.habit.data.repository.datasource.PreferenceDataSource
import com.habit.data.repository.datasourceImpl.LocalDataSourceImpl
import com.habit.data.repository.datasourceImpl.PreferenceDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    @Provides
    fun provideLocalDataSource(exerciseDao: ExerciseDao): LocalDataSource =
        LocalDataSourceImpl(exerciseDao = exerciseDao)
    @Provides
    fun providePreferenceDataSource(@ApplicationContext context: Context): PreferenceDataSource =
        PreferenceDataSourceImpl(context)
}