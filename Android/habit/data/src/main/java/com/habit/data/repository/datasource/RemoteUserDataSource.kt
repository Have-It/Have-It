package com.habit.data.repository.datasource

import com.habit.data.entity.request.DailyMissionValueSaveRequest
import com.habit.data.entity.response.DailyMissionValueSaveResponse
import com.habit.data.entity.response.HomeDataResponse
import com.habit.data.entity.response.MemberHomeInfoResponse
import com.habit.data.entity.response.MemberInfoResponse
import com.habit.data.entity.response.MemberSettingPageInfoResponse
import com.habit.data.entity.response.MissionSuccessResponse
import com.habit.data.entity.response.StatResponse
import retrofit2.Response

interface RemoteUserDataSource {
    suspend fun kakaoLogin(token: String): Response<MemberInfoResponse>
    suspend fun normalLogin(email: String, password: String): Response<MemberInfoResponse>
    suspend fun logout()
    suspend fun getMemberHomeInfo(): Response<MemberHomeInfoResponse>
    suspend fun getHomeData(): Response<HomeDataResponse>
    suspend fun getMemberSettingPageInfo(): Response<MemberSettingPageInfoResponse>
    suspend fun getStat(): Response<StatResponse>
    suspend fun postExerciseMissionSuccess(): Response<MissionSuccessResponse>
    suspend fun postSleepMissionSuccess(): Response<MissionSuccessResponse>
    suspend fun postFreeMissionSuccess(): Response<MissionSuccessResponse>
    suspend fun postDailyMissionValueSave(dailyMissionValueSaveRequest: DailyMissionValueSaveRequest): Response<DailyMissionValueSaveResponse>
}