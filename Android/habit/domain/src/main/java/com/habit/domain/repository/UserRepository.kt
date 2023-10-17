package com.habit.domain.repository

import com.habit.domain.NetworkResult
import com.habit.domain.model.home.HomeDataDto
import com.habit.domain.model.home.MemberHomeInfoDto
import com.habit.domain.model.mission.MissionDto
import com.habit.domain.model.user.MemberInfoDto
import com.habit.domain.model.user.MemberSettingPageInfoDto
import com.habit.domain.model.user.NormalLoginInfoDto
import com.habit.domain.model.user.StatDto

interface UserRepository {
    suspend fun kakaoLogin(token: String): NetworkResult<MemberInfoDto>
    suspend fun normalLogin(email: String, password: String): NetworkResult<MemberInfoDto>
    suspend fun logout()

    suspend fun putAccessToken(token: String)
    suspend fun getAccessToken(): String

    suspend fun putMission(freeMission: MissionDto)

    suspend fun putMemberInfo(memberInfoDto: MemberInfoDto)
    suspend fun getMemberInfo(): MemberInfoDto
    suspend fun putKakaoToken(kakaoToken: String)
    suspend fun getKakaoToken(): String

    suspend fun putNormalLoginInfo(email: String, pass: String)
    suspend fun getNormalLoginInfo(): NormalLoginInfoDto

    suspend fun deleteKakaoToken()
    suspend fun deleteNormalLoginInfo()
    suspend fun deleteAccessToken()

    suspend fun getMission(): MissionDto

    suspend fun getHomeData(): NetworkResult<HomeDataDto>
    suspend fun getMemberHomeInfo(): NetworkResult<MemberHomeInfoDto>
    suspend fun getMemberSettingPageInfo(): NetworkResult<MemberSettingPageInfoDto>
    suspend fun getStat(): NetworkResult<StatDto>
    suspend fun putStepData(step: Int)
    suspend fun getAndResetStepData(): Int
}