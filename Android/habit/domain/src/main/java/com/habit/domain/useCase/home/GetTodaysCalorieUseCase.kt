package com.habit.domain.useCase.home

import com.habit.domain.repository.MissionRepository
import java.util.Date

class GetTodaysCalorieUseCase(private val missionRepository: MissionRepository) {
    suspend operator fun invoke(begin: Date, end: Date): Int {
        return missionRepository.selectTodaysCalorie(begin, end)
    }
}