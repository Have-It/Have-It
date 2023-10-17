package com.habit.domain.useCase.login

import com.habit.domain.NetworkResult
import com.habit.domain.model.user.MemberInfoDto
import com.habit.domain.onSuccess
import com.habit.domain.repository.UserRepository

private const val TAG = "KakaoLoginUseCase"
class KakaoLoginUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(token: String): NetworkResult<MemberInfoDto> {
        return userRepository.kakaoLogin(token)
//        res.onSuccess {
//            return it
//        }
//        return MemberInfoDto()
    }
}