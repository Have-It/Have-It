package com.habit.data.interceptor

import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Interceptor
import android.util.Log
import com.google.gson.Gson
import com.habit.data.repository.datasource.PreferenceDataSource
import com.habit.domain.model.error.ErrorResponse
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import javax.inject.Inject

/**
 * 서버에 요청할 때 accessToken유효한지 검사
 * 유효하지 않다면 재발급 api 호출
 * refreshToken이 유효하다면 정상적으로 accessToken재발급 후 기존 api 동작 완료
 */
private const val TAG = "ResponseInterceptor_싸피"


class ResponseInterceptor @Inject constructor(
    private val preferenceDataSource: PreferenceDataSource
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response: Response = chain.proceed(request)

        var accessToken = ""
        var isRefreshable = false

        Log.d(TAG, "intercept: 지금 코드 ${response.code}")
        Log.d(TAG, "intercept: 지금 네트워크 리스폰스 ${response.networkResponse}")

        Log.d(TAG, "intercept: *-----------------------------------------*")
        Log.d(TAG, "intercept: ${response.headers}")
        Log.d(TAG, "intercept: *-----------------------------------------*")
        Log.d(TAG, "intercept: ${response.headers("New-Access-Token").size}")
        
        if(response.headers("New-Access-Token").size == 1){//기존 엑세스 토큰 만료
            Log.d(TAG, "intercept: 만료되었지만 body안에 내용은")
            Log.d(TAG, "intercept: ${response.body}")
            if(response.headers("New-Access-Token")[0].toString().split(" ").size == 2){ //혹시 몰라서 분기처리함
                Log.d(TAG, "intercept: 새로운 엑세스 토큰 : ${response.headers("New-Access-Token")[0].toString().split(" ")[1]}")
                preferenceDataSource.putAccessToken(response.headers("New-Access-Token")[0].toString().split(" ")[1])
            }else{
                //강제 로그아웃
            }
        }
        
        when (response.code) {
            400 -> {
                Log.d(TAG, "intercept: 에러 : 400 에러입니다.")
            }

            401 -> { // 여러 에러들 종합 (에러 메시지로 확인하자.)
                Log.d(TAG, "intercept: 401 : ")
                Log.d(TAG, "intercept: 다시 로그인 해야합니다.")
                throw (IOException("required_re_login"))
            }

//            403 -> {
//                Log.d(TAG, "intercept: 에러 : 403 에러입니다.")
//                val errorResponse = parseErrorResponse(response.body)
//                Log.d(TAG, "intercept: 에러 바디 파싱 !!!!!!!!!! ${errorResponse}")
//
//                // 에러 코드로 분기
//                when (errorResponse.errorCode) {
//                    "Auth-009" -> {
//                        Log.d(TAG, "intercept: 다시 로그인 해야합니다.")
//                        throw (IOException("required_re_login"))
//                    }
//                }
//            }

            404 -> {
                Log.d(TAG, "intercept: 에러 : 404 에러입니다.")
            }

            500 -> { // 서버에러
                Log.d(TAG, "intercept: 에러 : 500 에러입니다.")
            }
        }


        return response
    }

    private fun parseErrorResponse(responseBody: ResponseBody?): ErrorResponse {
        val gson = Gson()
        return gson.fromJson(responseBody?.charStream(), ErrorResponse::class.java)
    }

}