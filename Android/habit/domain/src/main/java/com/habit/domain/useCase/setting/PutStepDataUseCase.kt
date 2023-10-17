package com.habit.domain.useCase.setting

import com.habit.domain.repository.UserRepository

class PutStepDataUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(step: Int) {
        userRepository.putStepData(step)
    }
}