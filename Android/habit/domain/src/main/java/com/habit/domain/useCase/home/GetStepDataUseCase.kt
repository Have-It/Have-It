package com.habit.domain.useCase.home

import com.habit.domain.model.mission.SleepSessionDto
import com.habit.domain.repository.HealthConnectRepository
import java.time.ZonedDateTime

class GetStepDataUseCase(private val healthConnectRepository: HealthConnectRepository) {
    suspend operator fun invoke(date: ZonedDateTime): Long{
        return healthConnectRepository.getStepData(date)
    }
}