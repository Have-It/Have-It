package com.habit.data.api

import android.util.Log
import com.habit.domain.NetworkResult

private const val TAG = "HandleAPI"
internal inline fun <T> handleApi(transform: () -> T): NetworkResult<T> = try {
    NetworkResult.Success(transform.invoke())
} catch (e: Exception) {
    when (e) {
        else -> {
            Log.d(TAG, "handleApi: message ${e.message}")
            Log.d(TAG, "handleApi: cause ${e.cause}")
            Log.d(TAG, "handleApi: localized ${e.localizedMessage}")
            Log.d(TAG, "handleApi: stack ${e.stackTrace}")
            Log.d(TAG, "handleApi: suppressed ${e.suppressed}")
            NetworkResult.Error(e)
        }
    }
}