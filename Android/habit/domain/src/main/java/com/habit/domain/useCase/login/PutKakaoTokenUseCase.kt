package com.habit.domain.useCase.login

import com.habit.domain.repository.UserRepository

class PutKakaoTokenUseCase (private val userRepository: UserRepository) {
    suspend operator fun invoke(kakaoToken: String){
        userRepository.putKakaoToken(kakaoToken)
    }
}