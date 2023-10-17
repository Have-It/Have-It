package com.habit.domain.useCase.home

import com.habit.domain.NetworkResult
import com.habit.domain.model.home.HomeDataDto
import com.habit.domain.model.home.MemberHomeInfoDto
import com.habit.domain.onSuccess
import com.habit.domain.repository.UserRepository

class GetMemberHomeInfoUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): NetworkResult<MemberHomeInfoDto> {
        return userRepository.getMemberHomeInfo()
//        res.onSuccess {
//            return it
//        }
//        return MemberHomeInfoDto("", "")
    }
}