package com.habit.domain.useCase.login

import com.habit.domain.model.user.MemberInfoDto
import com.habit.domain.repository.UserRepository

class PutMemberInfoUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(memberInfoDto: MemberInfoDto) {
        val res = userRepository.putMemberInfo(memberInfoDto)
    }
}