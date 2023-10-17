package com.habit.ui.main

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import com.habit.data.alarm.AlarmController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "MainActivity"
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val alarmManager: AlarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(alarmManager.canScheduleExactAlarms()){
                val alarmController: AlarmController = AlarmController(applicationContext)
                alarmController.setAlarm()
            }else{
                Intent().apply {
                    action = ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                }.also {
                    startActivity(it)
                }
            }
        }else{
            val alarmController: AlarmController = AlarmController(applicationContext)
            alarmController.setAlarm()
        }




        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        setContent {
            HabitApp()
        }
    }
}



