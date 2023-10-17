package com.habit.data.service

import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import com.google.gson.Gson
import com.habit.data.entity.ExerciseEntity
import com.habit.data.entity.ExerciseRawData
import com.habit.data.repository.datasource.LocalDataSource
import com.habit.data.repository.datasource.PreferenceDataSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.Date
import javax.inject.Inject

private const val TAG = "DataLayerListenerServic"

@AndroidEntryPoint
class DataLayerListenerService() : WearableListenerService() {

    @Inject
    lateinit var localDataSource: LocalDataSource

    @Inject
    lateinit var preferenceDataSource: PreferenceDataSource

    private val gson: Gson = Gson()

    companion object {
        private const val SEND_DATA_PATH = "/send-data"
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)
        when (messageEvent.path) {
            SEND_DATA_PATH -> {
                Log.d(
                    TAG,
                    "onMessageReceived: 메시지 받음! ${messageEvent.data.toString(Charsets.UTF_8)}"
                )
                try {
                    val messageResult = gson.fromJson(
                        messageEvent.data.toString(Charsets.UTF_8),
                        ExerciseRawData::class.java
                    )
                    val userId = preferenceDataSource.getMemberInfo().memberId
                    val exerciseEntity = ExerciseEntity(
                        null,
                        userId,
                        messageResult.exerciseType,
                        Date(),
                        messageResult.totalCalorie
                    )
                    Log.d(TAG, "onMessageReceived: ${exerciseEntity}")
                    CoroutineScope(Dispatchers.IO).launch{
                        localDataSource.insertExercise(exerciseEntity)
                    }

                }catch (e: Exception){
                    Log.d(TAG, "onMessageReceived: 저장 실패")
                }

            }
        }
    }
}