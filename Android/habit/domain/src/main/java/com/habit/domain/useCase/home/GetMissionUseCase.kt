package com.habit.domain.useCase.home

import com.habit.domain.model.mission.MissionDto
import com.habit.domain.repository.UserRepository

class GetMissionUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): MissionDto{
        return userRepository.getMission()
    }
}