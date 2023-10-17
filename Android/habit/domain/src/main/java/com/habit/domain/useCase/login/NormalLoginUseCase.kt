package com.habit.domain.useCase.login

import com.habit.domain.NetworkResult
import com.habit.domain.model.user.MemberInfoDto
import com.habit.domain.onSuccess
import com.habit.domain.repository.UserRepository

class NormalLoginUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String, password: String): NetworkResult<MemberInfoDto> {
        return userRepository.normalLogin(email, password)
//        res.onSuccess {
//            return it
//        }
//        return MemberInfoDto()
    }
//    suspend operator fun invoke(email: String, password: String): NetworkResult<LoginInfoDto> {
//        val res = userRepository.normalLogin(email, password)
//        return res
//    }
}