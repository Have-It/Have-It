package com.habit.data.api

import com.habit.data.entity.request.DailyMissionValueSaveRequest
import com.habit.data.entity.request.NormalLoginRequest
import com.habit.data.entity.response.DailyMissionValueSaveResponse
import com.habit.data.entity.response.HomeDataResponse
import com.habit.data.entity.response.MemberHomeInfoResponse
import com.habit.data.entity.response.MemberInfoResponse
import com.habit.data.entity.response.MemberSettingPageInfoResponse
import com.habit.data.entity.response.MissionSuccessResponse
import com.habit.data.entity.response.Post
import com.habit.data.entity.response.StatResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MainService {

    @GET("posts/1")
    suspend fun getFirstPost(): Response<Post>

    @GET("posts")
    suspend fun getAllPosts(): Response<List<Post>>

    @POST("/member/kakao/login/mobile")
    suspend fun kakaoLogin(
        @Query("token") token: String
    ): Response<MemberInfoResponse>

    @POST("/member/login")
    suspend fun normalLogin(
        @Body normalLoginRequest: NormalLoginRequest
    ): Response<MemberInfoResponse>

    @POST("/member/logout")
    suspend fun logout(): Response<String>

    @GET("/member/home")
    suspend fun getMemberHomeInfo(): Response<MemberHomeInfoResponse>

    @GET("/member/setting")
    suspend fun getMemberSettingPageInfo(): Response<MemberSettingPageInfoResponse>

    @GET("/data/mobile/home")
    suspend fun getHomeData(): Response<HomeDataResponse>

    @GET("/data/mobile/setting")
    suspend fun getStat(): Response<StatResponse>

    @POST("/success/exercise")
    suspend fun postExerciseMissionSuccess(): Response<MissionSuccessResponse>

    @POST("/success/sleep")
    suspend fun postSleepMissionSuccess(): Response<MissionSuccessResponse>

    @POST("/success/customMission")
    suspend fun postFreeMissionSuccess(): Response<MissionSuccessResponse>

    @POST("/mission/save")
    suspend fun postDailyMissionValueSave(
        @Body dailyMissionValueSaveRequest: DailyMissionValueSaveRequest
    ): Response<DailyMissionValueSaveResponse>
}

//code : String
//message : String
//status : Int
//detail : String
//
//error
