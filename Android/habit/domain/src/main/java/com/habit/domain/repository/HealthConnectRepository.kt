package com.habit.domain.repository

import com.habit.domain.model.mission.SleepSessionDto
import java.time.ZonedDateTime

interface HealthConnectRepository {
    suspend fun getSleepData(date: ZonedDateTime): SleepSessionDto
    suspend fun getStepData(date: ZonedDateTime): Long
}