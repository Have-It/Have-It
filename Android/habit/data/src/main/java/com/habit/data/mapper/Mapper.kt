package com.habit.data.mapper

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.health.connect.client.records.SleepSessionRecord
import com.habit.data.entity.ExerciseEntity
import com.habit.data.entity.request.KakaoLoginRequest
import com.habit.data.entity.request.MissionRequest
import com.habit.data.entity.request.SaveMemberInfoRequest
import com.habit.data.entity.response.HomeDataResponse
import com.habit.data.entity.response.MemberHomeInfoResponse
import com.habit.data.entity.response.MemberInfoResponse
import com.habit.data.entity.response.MemberSettingPageInfoResponse
import com.habit.data.entity.response.MissionResponse
import com.habit.data.entity.response.MissionSuccessResponse
import com.habit.data.entity.response.NormalLoginInfoResponse
import com.habit.data.entity.response.Post
import com.habit.data.entity.response.SleepSessionResponse
import com.habit.data.entity.response.StatResponse
import com.habit.domain.model.home.HomeDataDto
import com.habit.domain.model.home.MemberHomeInfoDto
import com.habit.domain.model.mission.ExerciseDto
import com.habit.domain.model.mission.MissionDto
import com.habit.domain.model.mission.MissionSuccessDto
import com.habit.domain.model.mission.SleepSessionDto
import com.habit.domain.model.user.KakoLoginTokenDto
import com.habit.domain.model.user.MemberInfoDto
import com.habit.domain.model.user.MemberSettingPageInfoDto
import com.habit.domain.model.user.NormalLoginInfoDto
import com.habit.domain.model.user.StatDto
import java.time.Duration
import java.time.ZoneId

private const val TAG = "Mapper"

//엔티티를 가공하여 도메인의 모델(dto)로 바꾸는 매퍼 모음

/******************* toDomain mappers *******************/
fun Post.toDomain(): com.habit.domain.model.example.Post {
    return com.habit.domain.model.example.Post(
        body = body,
        id = id,
        title = title,
        userId = userId
    )
}

fun MemberInfoResponse.toDomain(): MemberInfoDto {
    return MemberInfoDto(
        memberId = memberId,
        nickName = nickName,
        profileImage = profileImage,
        settingImage = settingImage,
        grantType = grantType,
        accessToken = accessToken,
        refreshToken = refreshToken,
        refreshTokenExpirationTime = refreshTokenExpirationTime
    )
}

fun NormalLoginInfoResponse.toDomain(): NormalLoginInfoDto {
    return NormalLoginInfoDto(
        email = email,
        pass = pass
    )
}

fun MissionResponse.toDomain(): MissionDto {
    return MissionDto(
        userId = userId,
        calorie = calorie,
        hour = hour,
        minute = minute,
        freeMissionType = freeMissionType,
        freeMissionDetail = freeMissionDetail
    )
}

fun SaveMemberInfoRequest.toDomain(): MemberInfoDto {
    return MemberInfoDto(
        memberId = memberId,
        nickName = nickName,
        profileImage = profileImage,
        settingImage = settingImage,
        grantType = grantType,
        accessToken = accessToken,
        refreshToken = refreshToken,
        refreshTokenExpirationTime = refreshTokenExpirationTime
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun List<SleepSessionResponse>.toDomain(): SleepSessionDto {
    var awake: Duration = Duration.ZERO
    var rem: Duration = Duration.ZERO
    var light: Duration = Duration.ZERO
    var deep: Duration = Duration.ZERO
    var totalSleep: Duration = Duration.ZERO

    if (this.isNotEmpty()) {
        this.forEach {
            it.stages.forEach {
                val start = it.startTime.atZone(ZoneId.systemDefault()).toLocalDateTime()
                val end = it.endTime.atZone(ZoneId.systemDefault()).toLocalDateTime()
                val durationValue = Duration.between(start, end)
                totalSleep += durationValue
                if (it.stage == SleepSessionRecord.STAGE_TYPE_AWAKE) {
                    awake += durationValue
                } else if (it.stage == SleepSessionRecord.STAGE_TYPE_REM) {
                    rem += durationValue
                } else if (it.stage == SleepSessionRecord.STAGE_TYPE_LIGHT) {
                    light += durationValue
                } else if (it.stage == SleepSessionRecord.STAGE_TYPE_DEEP) {
                    deep += durationValue
                }
            }
        }
    }
    Log.d(TAG, "toDomain: mapper!! ${totalSleep}")
    return SleepSessionDto(totalSleep, awake, rem, light, deep)
}

fun HomeDataResponse.toDomain(): HomeDataDto {
    return HomeDataDto(
        totalSuccess = totalSuccess,
        coin = coin,
        calorieMissionSuccess = calorieMissionSuccess,
        sleepMissionSuccess = sleepMissionSuccess,
        freeMissionSuccess = freeMissionSuccess
    )
}

fun MissionSuccessResponse.toDomain(): MissionSuccessDto {
    return MissionSuccessDto(
        message = message,
        calorieMissionSuccess = calorieMissionSuccess,
        sleepMissionSuccess = sleepMissionSuccess,
        freeMissionSuccess = freeMissionSuccess,
    )
}

fun ExerciseEntity.toDomain(): ExerciseDto {
    return ExerciseDto(
        id = id ?: -1,
        userId = userId,
        exerciseType = exerciseType,
        time = time,
        calorie = calorie
    )
}

fun MemberHomeInfoResponse.toDomain(): MemberHomeInfoDto{
    return MemberHomeInfoDto(
        nickName = nickName,
        profileImage = profileImage
    )
}

fun MemberSettingPageInfoResponse.toDomain(): MemberSettingPageInfoDto{
    return MemberSettingPageInfoDto(
        nickName = nickName,
        settingPageProfileImage = settingPageProfileImage
    )
}

fun StatResponse.toDomain(): StatDto{
    return StatDto(
        characterWeight = characterWeight,
        characterDarkcircle = characterDarkcircle
    )
}

/******************* toData mappers *******************/
fun KakoLoginTokenDto.toData(): KakaoLoginRequest {
    return KakaoLoginRequest(
        kakaoToken = kakaoToken
    )
}

fun MissionDto.toData(): MissionRequest {
    return MissionRequest(
        userId = userId,
        calorie = calorie,
        hour = hour,
        minute = minute,
        freeMissionType = freeMissionType,
        freeMissionDetail = freeMissionDetail
    )
}

fun MemberInfoDto.toData(): SaveMemberInfoRequest {
    return SaveMemberInfoRequest(
        memberId = memberId,
        nickName = nickName,
        profileImage = profileImage,
        settingImage = settingImage,
        grantType = grantType,
        accessToken = accessToken,
        refreshToken = refreshToken,
        refreshTokenExpirationTime = refreshTokenExpirationTime
    )
}

fun ExerciseDto.toData(): ExerciseEntity {
    return ExerciseEntity(
        id = id,
        userId = userId,
        exerciseType = exerciseType,
        time = time,
        calorie = calorie
    )
}