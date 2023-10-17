package com.habit.domain.useCase.main

import com.habit.domain.model.user.MemberInfoDto
import com.habit.domain.repository.UserRepository

class GetMemberInfoUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): MemberInfoDto{
        return userRepository.getMemberInfo()
    }
}