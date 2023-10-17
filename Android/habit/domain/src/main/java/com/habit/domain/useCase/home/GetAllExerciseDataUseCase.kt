package com.habit.domain.useCase.home

import com.habit.domain.model.mission.ExerciseDto
import com.habit.domain.repository.MissionRepository

class GetAllExerciseDataUseCase (private val missionRepository: MissionRepository){
    suspend operator fun invoke(): List<ExerciseDto>{
        return missionRepository.selectExerciseAll()
    }
}