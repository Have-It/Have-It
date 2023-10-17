package com.habit.di

import android.content.Context
//import com.chuckerteam.chucker.api.ChuckerCollector
//import com.chuckerteam.chucker.api.ChuckerInterceptor
//import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.habit.data.api.ApiClient.BASE_URL
import com.habit.data.api.MainService
import com.habit.data.interceptor.RequestInterceptor
import com.habit.data.interceptor.ResponseInterceptor
import com.habit.data.repository.datasource.PreferenceDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

//    @Provides
//    @Singleton
//    fun provideChuckerCollector(@ApplicationContext context: Context): ChuckerCollector {
//        return ChuckerCollector(
//            context,
//            showNotification = true,
//            retentionPeriod = RetentionManager.Period.FOREVER
//        )
//    }

//    @Provides
//    @Singleton
//    fun provideChuckerInterceptor(
//        @ApplicationContext context: Context,
//        chuckerCollector: ChuckerCollector
//    ): ChuckerInterceptor {
//        return ChuckerInterceptor.Builder(context)
//            .collector(chuckerCollector)
//            .maxContentLength(250_000L)
//            // List of headers to replace with ** in the Chucker UI
//            .redactHeaders("Auth-Token", "Bearer")
//            // Read the whole response body even when the client does not consume the response completely.
//            // This is useful in case of parsing errors or when the response body
//            // is closed before being read like in Retrofit with Void and Unit types.
//            .alwaysReadResponseBody(true)
//            .build()
//    }


    @Singleton
    @Provides
    fun provideOkHttpClient(
      //  chuckerInterceptor: ChuckerInterceptor,
        preferenceDataSource: PreferenceDataSource
    ): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(ResponseInterceptor(preferenceDataSource)) // Refresh Token
            .addInterceptor(RequestInterceptor(preferenceDataSource)) // JWT 자동 헤더 전송
          //  .addInterceptor(chuckerInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    //    }
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()


        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideMainAPIService(retrofit: Retrofit): MainService =
        retrofit.create(MainService::class.java)
}