package com.habit.domain.useCase.login

import com.habit.domain.model.user.NormalLoginInfoDto
import com.habit.domain.repository.UserRepository

class GetNormalLoginInfoUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): NormalLoginInfoDto {
        return userRepository.getNormalLoginInfo()
    }
}