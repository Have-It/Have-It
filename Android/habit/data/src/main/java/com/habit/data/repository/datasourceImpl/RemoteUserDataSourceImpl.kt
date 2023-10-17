package com.habit.data.repository.datasourceImpl

import com.habit.data.api.MainService
import com.habit.data.entity.request.DailyMissionValueSaveRequest
import com.habit.data.entity.request.NormalLoginRequest
import com.habit.data.entity.response.DailyMissionValueSaveResponse
import com.habit.data.entity.response.HomeDataResponse
import com.habit.data.entity.response.MemberHomeInfoResponse
import com.habit.data.entity.response.MemberInfoResponse
import com.habit.data.entity.response.MemberSettingPageInfoResponse
import com.habit.data.entity.response.MissionSuccessResponse
import com.habit.data.entity.response.StatResponse
import com.habit.data.repository.datasource.RemoteUserDataSource
import retrofit2.Response

private const val TAG = "RemoteUserDataSourceImp"

class RemoteUserDataSourceImpl(private val apiService: MainService) : RemoteUserDataSource {

    override suspend fun kakaoLogin(token: String): Response<MemberInfoResponse> {
        return apiService.kakaoLogin(token)
    }

    override suspend fun normalLogin(
        email: String,
        password: String
    ): Response<MemberInfoResponse> {
        return apiService.normalLogin(NormalLoginRequest(email, password))
    }

    override suspend fun logout() {
        apiService.logout()
    }

    override suspend fun getMemberHomeInfo(): Response<MemberHomeInfoResponse> {
        return apiService.getMemberHomeInfo()
    }

    override suspend fun getHomeData(): Response<HomeDataResponse> {
        return apiService.getHomeData()
    }

    override suspend fun getMemberSettingPageInfo(): Response<MemberSettingPageInfoResponse> {
        return apiService.getMemberSettingPageInfo()
    }

    override suspend fun getStat(): Response<StatResponse> {
        return apiService.getStat()
    }

    override suspend fun postExerciseMissionSuccess(): Response<MissionSuccessResponse> {
        return apiService.postExerciseMissionSuccess()
    }

    override suspend fun postSleepMissionSuccess(): Response<MissionSuccessResponse> {
        return apiService.postSleepMissionSuccess()
    }

    override suspend fun postFreeMissionSuccess(): Response<MissionSuccessResponse> {
        return apiService.postFreeMissionSuccess()
    }

    override suspend fun postDailyMissionValueSave(dailyMissionValueSaveRequest: DailyMissionValueSaveRequest): Response<DailyMissionValueSaveResponse> {
        return apiService.postDailyMissionValueSave(dailyMissionValueSaveRequest)
    }
}