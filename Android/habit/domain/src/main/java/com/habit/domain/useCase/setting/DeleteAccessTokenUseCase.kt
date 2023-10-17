package com.habit.domain.useCase.setting

import com.habit.domain.repository.UserRepository

class DeleteAccessTokenUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(){
        userRepository.deleteAccessToken()
    }
}