package com.habit.domain.useCase.home

import com.habit.domain.model.mission.SleepSessionDto
import com.habit.domain.repository.HealthConnectRepository
import java.time.ZonedDateTime

class GetSleepDataUseCase(private val healthConnectRepository: HealthConnectRepository) {
    suspend operator fun invoke(date: ZonedDateTime): SleepSessionDto {
        return healthConnectRepository.getSleepData(date)
    }
}