package com.habit.di


import android.content.Context
import androidx.room.Room
import com.habit.data.db.ExerciseDao
import com.habit.data.db.ExerciseDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)//이 application이 살아있는 동안은 동일한 인스턴스를 사용
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ExerciseDataBase::class.java, "exercise_database").fallbackToDestructiveMigration()
            .build()
    @Provides
    fun provideExerciseDao(exerciseDataBase: ExerciseDataBase) : ExerciseDao = exerciseDataBase.exerciseDao()
//
//    @Provides
//    fun provideMovieRemoteKeysDao(movieDB: MovieDB) : MovieRemoteKeysDao = movieDB.movieRemoteKeysDao()
}