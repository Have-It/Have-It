package com.habit.data.repository

import com.habit.data.api.handleApi
import com.habit.data.mapper.toData
import com.habit.data.mapper.toDomain
import com.habit.data.repository.datasource.PreferenceDataSource
import com.habit.data.repository.datasource.RemoteUserDataSource
import com.habit.domain.NetworkResult
import com.habit.domain.model.home.HomeDataDto
import com.habit.domain.model.home.MemberHomeInfoDto
import com.habit.domain.model.mission.MissionDto
import com.habit.domain.model.user.MemberInfoDto
import com.habit.domain.model.user.MemberSettingPageInfoDto
import com.habit.domain.model.user.NormalLoginInfoDto
import com.habit.domain.model.user.StatDto
import com.habit.domain.repository.UserRepository
import javax.inject.Inject

private const val TAG = "UserRepositoryImpl"

class UserRepositoryImpl @Inject constructor(
    private val preferenceDatasource: PreferenceDataSource,
    private val remoteDataSource: RemoteUserDataSource
) : UserRepository {
    //    override suspend fun kakaoLogin(kakaoLoginRequest: KakaoLoginRequest): NetworkResult<LoginInfoDto> {
//        return handleApi {
//            remoteDataSource.kakaoLogin(kakaoLoginRequest).body()!!.toDomain()
//        }
//    }
    override suspend fun kakaoLogin(token: String): NetworkResult<MemberInfoDto> {
        return handleApi {
            remoteDataSource.kakaoLogin(token).body()!!.toDomain()
        }
    }

    override suspend fun normalLogin(
        email: String,
        password: String
    ): NetworkResult<MemberInfoDto> {
        return handleApi {
            remoteDataSource.normalLogin(email, password).body()!!.toDomain()
        }
    }

    override suspend fun logout() {
        remoteDataSource.logout()
    }

    override suspend fun putAccessToken(token: String) {
        preferenceDatasource.putAccessToken(token)
    }

    override suspend fun getAccessToken(): String {
        return preferenceDatasource.getAccessToken()
    }

    override suspend fun putMission(mission: MissionDto) {
        preferenceDatasource.putMission(mission.toData())
    }

    override suspend fun putMemberInfo(loginInfoDto: MemberInfoDto) {
        preferenceDatasource.putMemberInfo(loginInfoDto.toData())
    }

    override suspend fun getMemberInfo(): MemberInfoDto {
        return preferenceDatasource.getMemberInfo().toDomain()
    }

    override suspend fun putKakaoToken(kakaoToken: String) {
        preferenceDatasource.putKakaoToken(kakaoToken)
    }

    override suspend fun putNormalLoginInfo(email: String, pass: String) {
        preferenceDatasource.putNormalLoginInfo(email, pass)

    }

    override suspend fun getKakaoToken(): String {
        return preferenceDatasource.getKakaoToken()
    }

    override suspend fun getNormalLoginInfo(): NormalLoginInfoDto {
        return preferenceDatasource.getNormalLoginInfo().toDomain()
    }

    override suspend fun deleteKakaoToken() {
        preferenceDatasource.deleteKakoToken()
    }

    override suspend fun deleteNormalLoginInfo() {
        preferenceDatasource.deleteNormalLoginInfo()
    }

    override suspend fun deleteAccessToken() {
        preferenceDatasource.deleteAccessToken()
    }

    override suspend fun getMission(): MissionDto {
        return preferenceDatasource.getMission().toDomain()
    }

    override suspend fun getHomeData(): NetworkResult<HomeDataDto> {
        return handleApi {
            remoteDataSource.getHomeData().body()!!.toDomain()
        }
    }

    override suspend fun getMemberHomeInfo(): NetworkResult<MemberHomeInfoDto> {
        return handleApi {
            remoteDataSource.getMemberHomeInfo().body()!!.toDomain()
        }
    }

    override suspend fun getMemberSettingPageInfo(): NetworkResult<MemberSettingPageInfoDto> {
        return handleApi {
            remoteDataSource.getMemberSettingPageInfo().body()!!.toDomain()
        }
    }

    override suspend fun getStat(): NetworkResult<StatDto> {
        return handleApi {
            remoteDataSource.getStat().body()!!.toDomain()
        }
    }

    override suspend fun putStepData(step: Int) {
        preferenceDatasource.putStepData(step)
    }

    override suspend fun getAndResetStepData(): Int {
        return preferenceDatasource.getAndResetStepData()
    }

}