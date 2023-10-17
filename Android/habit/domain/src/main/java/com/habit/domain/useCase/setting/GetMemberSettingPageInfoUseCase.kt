package com.habit.domain.useCase.setting

import com.habit.domain.NetworkResult
import com.habit.domain.model.home.MemberHomeInfoDto
import com.habit.domain.model.user.MemberSettingPageInfoDto
import com.habit.domain.onSuccess
import com.habit.domain.repository.UserRepository

class GetMemberSettingPageInfoUseCase (private val userRepository: UserRepository) {
    suspend operator fun invoke(): NetworkResult<MemberSettingPageInfoDto> {
        return userRepository.getMemberSettingPageInfo()
//        res.onSuccess {
//            return it
//        }
//        return MemberSettingPageInfoDto("", "")
    }
}