package com.habit.domain.useCase.login

import com.habit.domain.repository.UserRepository

class GetKakaoTokenUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): String {
        return userRepository.getKakaoToken()
    }
}