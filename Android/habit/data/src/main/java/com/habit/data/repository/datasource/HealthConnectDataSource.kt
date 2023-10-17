package com.habit.data.repository.datasource

import com.habit.data.entity.response.SleepSessionResponse
import java.time.ZonedDateTime

interface HealthConnectDataSource {
    suspend fun readSleepDataOneDay(date: ZonedDateTime): List<SleepSessionResponse>
    suspend fun readStepDataOneDay(date: ZonedDateTime): Long
}