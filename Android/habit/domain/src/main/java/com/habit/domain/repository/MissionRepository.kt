package com.habit.domain.repository

import com.habit.domain.NetworkResult
import com.habit.domain.model.mission.ExerciseDto
import com.habit.domain.model.mission.MissionSuccessDto
import java.util.Date

interface MissionRepository {
    suspend fun postExerciseMissionSuccess(): NetworkResult<MissionSuccessDto>
    suspend fun postSleepMissionSuccess(): NetworkResult<MissionSuccessDto>
    suspend fun postFreeMissionSuccess(): NetworkResult<MissionSuccessDto>
    suspend fun selectExerciseAll(): List<ExerciseDto>
    suspend fun selectTodaysCalorie(begin: Date, end: Date): Int
    suspend fun selectTodaysExercise(begin: Date, end: Date): List<ExerciseDto>
}