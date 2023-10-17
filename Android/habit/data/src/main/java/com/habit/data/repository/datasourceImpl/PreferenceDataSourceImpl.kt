package com.habit.data.repository.datasourceImpl

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.habit.data.entity.request.MissionRequest
import com.habit.data.entity.request.SaveMemberInfoRequest
import com.habit.data.entity.response.MissionResponse
import com.habit.data.entity.response.NormalLoginInfoResponse
import com.habit.data.repository.datasource.PreferenceDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val TAG = "PreferenceDataSourceImp"

class PreferenceDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context,
) : PreferenceDataSource {

    companion object {
        private const val PREFERENCE_NAME = "habit_pref"
        const val ACCESS_TOKEN = "access_token"
        const val MISSION = "mission"
        const val CALORIE_GOAL = "calorie_goal"
        const val SLEEP_GOAL = "sleep_goal" //분단위
        const val FREE_GOAL_TYPE = "free_goal_type"
        const val FREE_GOAL_DESCRIPTION = "free_goal_description"
        const val MEMBER_INFO = "member_info"
        const val KAKAO_TOKEN = "kakao_token" //자동 로그인을 위한 카카오 토큰
        const val EMAIL = "email" //자동 로그인을 위한 이메일
        const val PASS = "pass" //자동 로그인을 위한 패스워드
        const val STEP_DATA = "step_data" //걸음 수 데이터
    }

    private fun getPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    val prefs by lazy { getPreference(context) }
    private val editor by lazy { prefs.edit() }
    private val gson = Gson()


    override fun putAccessToken(data: String?) {
        editor.putString(ACCESS_TOKEN, data)
        editor.apply()

        Log.d(TAG, "putAccessToken: ${prefs.getString(ACCESS_TOKEN, "")}")
    }

    override fun getAccessToken(): String {
        return prefs.getString(ACCESS_TOKEN, "") ?: ""
    }

    override fun putMemberInfo(saveMemberInfoRequest: SaveMemberInfoRequest) {
        editor.putString(MEMBER_INFO, gson.toJson(saveMemberInfoRequest))
        editor.apply()
        Log.d(TAG, "putMemberInfo: 유저정보 저장결과 ${prefs.getString(MEMBER_INFO, "")}")
    }

    override fun getMemberInfo(): SaveMemberInfoRequest {
        val memberInfoJson = prefs.getString(MEMBER_INFO, "")
        if (memberInfoJson == "") {
            return SaveMemberInfoRequest(-1, "", "", "", "", "", "", "")
        } else {
            return gson.fromJson(memberInfoJson, SaveMemberInfoRequest::class.java)
        }
    }

    override fun putKakaoToken(kakoToken: String) {
        editor.putString(KAKAO_TOKEN, kakoToken)
        editor.apply()
    }

    override fun putNormalLoginInfo(email: String, pass: String) {
        editor.putString(EMAIL, email)
        editor.putString(PASS, pass)
        editor.apply()
    }

    override fun getKakaoToken(): String {
        return prefs.getString(KAKAO_TOKEN, "") ?: ""
    }

    override fun getNormalLoginInfo(): NormalLoginInfoResponse {
        val email = prefs.getString(EMAIL, "") ?: ""
        val pass = prefs.getString(PASS, "") ?: ""
        return NormalLoginInfoResponse(email, pass)
    }

    override fun deleteKakoToken() {
        editor.remove(KAKAO_TOKEN)
        editor.apply()
        Log.d(TAG, "deleteKakoToken: 토큰 삭제 결과 ${prefs.getString(KAKAO_TOKEN, "") ?: ""}")
    }

    override fun deleteNormalLoginInfo() {
        editor.remove(EMAIL)
        editor.remove(PASS)
        editor.apply()

        val email = prefs.getString(EMAIL, "") ?: ""
        val pass = prefs.getString(PASS, "") ?: ""
        Log.d(TAG, "deleteNormalLoginInfo: 일반 로그인 정보 삭제 결과 ${email}, ${pass}")
    }
    
    override fun deleteAccessToken(){
        editor.remove(ACCESS_TOKEN)
        editor.apply()
        Log.d(TAG, "deleteAccessToken: 엑세스토큰 삭제 결과 ${prefs.getString(ACCESS_TOKEN, "") ?: ""}")
    }

    override fun putStepData(step: Int) {
        editor.putString(STEP_DATA, step.toString())
        editor.apply()
        Log.d(TAG, "putStepData: 걸음수 넣기 결과 ${prefs.getString(STEP_DATA, "0")?:"0"}")
    }

    override fun getAndResetStepData(): Int {
        val res = prefs.getString(STEP_DATA, "0")?:"0"
        editor.putString(STEP_DATA, "0")
        editor.apply()
        Log.d(TAG, "putStepData: 걸음수 리셋 결과 ${prefs.getString(STEP_DATA, "0")?:"0"}")
        return res.toInt()
    }

    override fun putMission(mission: MissionRequest) {
        editor.putString(MISSION, gson.toJson(mission))
        editor.apply()
        Log.d(TAG, "putMission: ${prefs.getString(MISSION, "")}")
    }

    override fun getMission(): MissionResponse {
        val missionJson = prefs.getString(MISSION, "")
        if (missionJson == "") {
            return MissionResponse(-1,-1, -1, -1, "", "")
        } else {
            return gson.fromJson(missionJson, MissionResponse::class.java)
        }
    }
}