package com.habit.data.interceptor

import android.util.Log
import com.habit.data.repository.datasource.PreferenceDataSource
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "RequestInterceptor_싸피"
class RequestInterceptor @Inject constructor(
    private val preferenceDataSource: PreferenceDataSource
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        try {
            preferenceDataSource.getAccessToken().let { token ->
                if(token != ""){
                    builder.addHeader("Authorization", "Bearer ${token}")
                    return chain.proceed(builder.build())
                }
            }
        } catch (e: Exception) {
            return chain.proceed(chain.request())
        }
        return chain.proceed(chain.request())
    }

}