package com.habit.di

import android.app.Application
import com.habit.BuildConfig
import com.habit.data.alarm.AlarmController
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ApplicationClass : Application(){
    override fun onCreate() {
        super.onCreate()
        // Kakao SDK 초기화
        KakaoSdk.init(this,  BuildConfig.API_KEY)

    }
}