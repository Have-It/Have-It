package com.habit.data.repository

import com.habit.data.api.handleApi
import com.habit.data.mapper.toData
import com.habit.data.mapper.toDomain
import com.habit.data.repository.datasource.LocalDataSource
import com.habit.data.repository.datasource.RemoteUserDataSource
import com.habit.domain.NetworkResult
import com.habit.domain.model.mission.ExerciseDto
import com.habit.domain.model.mission.MissionSuccessDto
import com.habit.domain.repository.MissionRepository
import java.util.Date
import javax.inject.Inject

class MissionRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val localDataSource: LocalDataSource
) : MissionRepository {
    override suspend fun postExerciseMissionSuccess(): NetworkResult<MissionSuccessDto> {
        return handleApi { remoteUserDataSource.postExerciseMissionSuccess().body()!!.toDomain() }
    }

    override suspend fun postSleepMissionSuccess(): NetworkResult<MissionSuccessDto> {
        return handleApi { remoteUserDataSource.postSleepMissionSuccess().body()!!.toDomain() }
    }

    override suspend fun postFreeMissionSuccess(): NetworkResult<MissionSuccessDto> {
        return handleApi { remoteUserDataSource.postFreeMissionSuccess().body()!!.toDomain() }
    }

    override suspend fun selectExerciseAll(): List<ExerciseDto> {
        return localDataSource.selectAll().map { it.toDomain() }
    }

    override suspend fun selectTodaysCalorie(begin: Date, end: Date): Int {
        return localDataSource.selectTodaysCalorie(begin, end)
    }

    override suspend fun selectTodaysExercise(begin: Date, end: Date): List<ExerciseDto> {
        return localDataSource.selectTodaysExercise(begin, end).map { it.toDomain() }
    }


}