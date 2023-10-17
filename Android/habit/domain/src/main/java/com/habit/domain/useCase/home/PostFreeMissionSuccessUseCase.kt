package com.habit.domain.useCase.home

import com.habit.domain.NetworkResult
import com.habit.domain.model.mission.MissionSuccessDto
import com.habit.domain.onSuccess
import com.habit.domain.repository.MissionRepository

class PostFreeMissionSuccessUseCase(private val missionRepository: MissionRepository) {
    suspend operator fun invoke(): NetworkResult<MissionSuccessDto> {
        return missionRepository.postFreeMissionSuccess()
//        res.onSuccess {
//            return it
//        }
//        return MissionSuccessDto("", false, false, false)
    }
}