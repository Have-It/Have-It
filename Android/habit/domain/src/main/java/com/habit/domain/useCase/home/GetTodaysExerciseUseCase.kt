package com.habit.domain.useCase.home

import com.habit.domain.model.mission.ExerciseDto
import com.habit.domain.repository.MissionRepository
import java.util.Date

class GetTodaysExerciseUseCase(private val missionRepository: MissionRepository) {
    suspend operator fun invoke(begin: Date, end: Date): List<ExerciseDto>{
        return missionRepository.selectTodaysExercise(begin, end)
    }
}