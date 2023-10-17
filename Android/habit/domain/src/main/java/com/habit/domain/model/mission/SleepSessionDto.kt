package com.habit.domain.model.mission

import java.time.Duration

data class SleepSessionDto(
    val totalSleepTime: Duration,
    val awakeTime: Duration,
    val remTime: Duration,
    val lightTime: Duration,
    val deepTime: Duration
)