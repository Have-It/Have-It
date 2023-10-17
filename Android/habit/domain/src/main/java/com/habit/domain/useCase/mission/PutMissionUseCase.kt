package com.habit.domain.useCase.mission

import com.habit.domain.model.mission.MissionDto
import com.habit.domain.repository.UserRepository

class PutMissionUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(missionDto: MissionDto){
        userRepository.putMission(missionDto)
    }
}