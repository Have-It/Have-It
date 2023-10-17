package com.habit.domain.useCase.setting

import com.habit.domain.repository.UserRepository

data class DeleteKakaoTokenUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(){
        userRepository.deleteKakaoToken()
    }
}