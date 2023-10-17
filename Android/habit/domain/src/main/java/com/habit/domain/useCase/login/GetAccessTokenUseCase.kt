package com.habit.domain.useCase.login

import com.habit.domain.repository.UserRepository

class GetAccessTokenUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): String {
        return userRepository.getAccessToken()
    }
}