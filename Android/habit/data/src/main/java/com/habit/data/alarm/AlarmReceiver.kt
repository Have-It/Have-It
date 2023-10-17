package com.habit.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import com.habit.data.api.handleApi
import com.habit.data.entity.request.DailyMissionValueSaveRequest
import com.habit.data.repository.datasource.HealthConnectDataSource
import com.habit.data.repository.datasource.LocalDataSource
import com.habit.data.repository.datasource.PreferenceDataSource
import com.habit.data.repository.datasource.RemoteUserDataSource
import com.habit.domain.onError
import com.habit.domain.onSuccess
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Year
import java.time.ZonedDateTime
import java.util.Calendar
import javax.inject.Inject

private const val TAG = "AlarmReceiver"

@AndroidEntryPoint
class AlarmReceiver() : BroadcastReceiver() {
    @Inject
    lateinit var userDataSource: RemoteUserDataSource
    @Inject
    lateinit var localDataSource: LocalDataSource
    @Inject
    lateinit var preferenceDataSource: PreferenceDataSource

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent:Intent) {
        fireReminder(context, intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fireReminder(context: Context, intent: Intent) {
        val now = Calendar.getInstance() //현재 시각
        val beginTimeStandard = Calendar.getInstance().apply { //오늘 오전 3시
            set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), 3, 0, 0)
            add(Calendar.DATE, -1)
        }
        val end = now.time // 끝 시간
        var begin = beginTimeStandard.time// 시작 시간
        Log.d(TAG, "getHomeData: begin ${begin}")
       // val isRepeat = intent.getBooleanExtra("REPEAT", false)

        val nextDayCalendar = Calendar.getInstance().apply {
            add(Calendar.DATE, 1)
            set(Calendar.HOUR_OF_DAY, 2)
            set(Calendar.MINUTE, 51)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

//        if (!isRepeat) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val nextIntent = Intent(context, AlarmReceiver::class.java)
     //   nextIntent.putExtra("REPEAT", true)

        val pendingIntent = PendingIntent.getBroadcast(
            context, 0, nextIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            nextDayCalendar.timeInMillis,
            pendingIntent
        )
        Log.d(TAG, "fireReminder:  Recevice 재등록 ${nextDayCalendar}")

        CoroutineScope(Dispatchers.IO).launch {
            val stepCount = preferenceDataSource.getAndResetStepData()

            val totalCalorie = localDataSource.selectTodaysCalorie(begin, end)
            val res = handleApi {
                userDataSource.postDailyMissionValueSave(DailyMissionValueSaveRequest(totalCalorie, stepCount))
            }
            res.onSuccess {
               // Log.d(TAG, "fireReminder: 통신 성공 ${it.body()!!.recordDate}")
            }
            res.onError {
                Log.d(TAG, "fireReminder: 통신 실패!")
            }
        }
    }

}