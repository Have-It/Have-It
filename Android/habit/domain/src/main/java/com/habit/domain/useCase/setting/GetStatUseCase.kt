package com.habit.domain.useCase.setting

import com.habit.domain.NetworkResult
import com.habit.domain.model.user.MemberSettingPageInfoDto
import com.habit.domain.model.user.StatDto
import com.habit.domain.onSuccess
import com.habit.domain.repository.UserRepository

class GetStatUseCase (private val userRepository: UserRepository) {
    suspend operator fun invoke(): NetworkResult<StatDto> {
        return userRepository.getStat()
//        res.onSuccess {
//            return it
//        }
//        return StatDto(-1, -1)
    }
}
