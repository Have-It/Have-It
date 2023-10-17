package com.habit.data.repository.datasource

import com.habit.data.entity.request.MissionRequest
import com.habit.data.entity.request.SaveMemberInfoRequest
import com.habit.data.entity.response.MemberInfoResponse
import com.habit.data.entity.response.MissionResponse
import com.habit.data.entity.response.NormalLoginInfoResponse

interface PreferenceDataSource {
    fun putAccessToken(data: String?)
    fun getAccessToken(): String
    fun putMission(mission: MissionRequest)
    fun getMission(): MissionResponse
    fun putMemberInfo(saveMemberInfoRequest: SaveMemberInfoRequest)
    fun getMemberInfo():SaveMemberInfoRequest
    fun putKakaoToken(kakaoToken: String)
    fun putNormalLoginInfo(email: String, pass: String)
    fun getKakaoToken(): String
    fun getNormalLoginInfo(): NormalLoginInfoResponse
    fun deleteKakoToken()
    fun deleteNormalLoginInfo()
    fun deleteAccessToken()
    fun putStepData(step: Int)
    fun getAndResetStepData(): Int
}