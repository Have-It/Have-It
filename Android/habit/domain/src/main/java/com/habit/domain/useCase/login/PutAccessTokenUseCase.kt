package com.habit.domain.useCase.login

import com.habit.domain.repository.UserRepository

class PutAccessTokenUseCase (private val userRepository: UserRepository) {
    suspend operator fun invoke(token: String){
        userRepository.putAccessToken(token)
    }
}
