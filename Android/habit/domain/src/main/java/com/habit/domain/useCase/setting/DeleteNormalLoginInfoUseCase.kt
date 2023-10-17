package com.habit.domain.useCase.setting

import com.habit.domain.repository.UserRepository

class DeleteNormalLoginInfoUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(){
        userRepository.deleteNormalLoginInfo()
    }
}