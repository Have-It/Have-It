package com.habit.domain.useCase.login

import com.habit.domain.repository.UserRepository

class PutNormalLoginInfoUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String, pass: String){
        userRepository.putNormalLoginInfo(email, pass)
    }
}