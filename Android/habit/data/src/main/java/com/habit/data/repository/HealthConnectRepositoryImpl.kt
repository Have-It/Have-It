package com.habit.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.habit.data.mapper.toDomain
import com.habit.data.repository.datasource.HealthConnectDataSource
import com.habit.domain.model.mission.SleepSessionDto
import com.habit.domain.repository.HealthConnectRepository
import java.time.ZonedDateTime
import javax.inject.Inject

class HealthConnectRepositoryImpl @Inject constructor(
    private val healthConnectDataSource: HealthConnectDataSource
) : HealthConnectRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getSleepData(date: ZonedDateTime): SleepSessionDto {
        val sleepSessionResponseList = healthConnectDataSource.readSleepDataOneDay(date)
//        sleepSessionResponseList.forEach {
//            sleepSessionDtoList.add(it.toDomain())
//        }
        return sleepSessionResponseList.toDomain()
    }

    override suspend fun getStepData(date: ZonedDateTime): Long {
        return healthConnectDataSource.readStepDataOneDay(date)
    }
}