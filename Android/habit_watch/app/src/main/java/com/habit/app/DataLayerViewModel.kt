package com.habit.app

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.Wearable
import com.google.gson.Gson
import com.habit.data.model.ExerciseResultDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val TAG = "DataLayerViewModel"

class DataLayerViewModel(
    private val messageClient: MessageClient,
    private var capabilityClient: CapabilityClient,
    application: Application
) : AndroidViewModel(application){
    private val gson = Gson()
    companion object {
        private const val SEND_DATA_PATH = "/send-data"
        private const val CAPABILITY_NAME = "health_data_send_habit_phone"
    }

    private val _connectState = MutableStateFlow<ConnectState>(ConnectState.Loading)
    val connectState: StateFlow<ConnectState> = _connectState.asStateFlow()

    private var transcriptionNodeId: String? = null

    fun checkConnected(){
        viewModelScope.launch {
            Log.d(TAG, "onCreate: find....")
            val capabilityInfo =
                capabilityClient.getCapability(CAPABILITY_NAME, CapabilityClient.FILTER_REACHABLE).await()
            Log.d(TAG, "onCreate: ${capabilityInfo.nodes}")
            var id: String? = null
            for(node in capabilityInfo.nodes){
                if(node.isNearby==true){
                    Log.d(TAG, "checkConnected: have connected Phone!")
                    id = node.id
                    break
                }
            }
            if(id == null){
                Log.d(TAG, "checkConnected: 연결안됨!")
                transcriptionNodeId = null
                _connectState.emit(ConnectState.NotConnected)
            }else{
                Log.d(TAG, "checkConnected: 연결됨!")
                transcriptionNodeId = id
                _connectState.emit(ConnectState.Connected)
            }
        }
    }

    fun sendData(exerciseResultDto: ExerciseResultDto){
        val json = gson.toJson(exerciseResultDto)
        transcriptionNodeId?.also { nodeId ->
            val sendTask: Task<*> = messageClient.sendMessage(
                nodeId,
                SEND_DATA_PATH,
                json.toByteArray()
            ).apply {
                addOnSuccessListener {
                    Log.d(TAG, "sendData: success!")
                }
                addOnFailureListener {
                    Log.d(TAG, "sendData: fail...")
                }
            }
        }
    }

}

class DataLayerViewModelFactory(
    private val messageClient: MessageClient,
    private val capabilityClient: CapabilityClient,
    private val application: Application
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DataLayerViewModel::class.java)){
            return DataLayerViewModel(messageClient, capabilityClient, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}