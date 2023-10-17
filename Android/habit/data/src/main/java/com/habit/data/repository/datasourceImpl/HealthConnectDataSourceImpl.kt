package com.habit.data.repository.datasourceImpl

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.habit.data.entity.response.SleepSessionResponse
import com.habit.data.repository.datasource.HealthConnectDataSource
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlin.math.log

private const val TAG = "HealthConnectDataSource"

class HealthConnectDataSourceImpl @Inject constructor(
    private val healthConnectClient: HealthConnectClient
) : HealthConnectDataSource {

    //특정 날짜의 수면 데이터를 가져오는 함수
    //SleepSessionRecord를 SleepSessionData로 변경하여 반환한다
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun readSleepDataOneDay(date: ZonedDateTime): List<SleepSessionResponse> {
        val sessions = mutableListOf<SleepSessionResponse>()
        val startTime = date.minusDays(2)
        var standardTime = date

        if (date.hour >= 0 && date.hour <= 2) {
            standardTime = standardTime.minusDays(1)
        }
        Log.d(TAG, "readSleepDataOneDay: ${date} ${startTime} ${standardTime}")

        val request = ReadRecordsRequest(
            recordType = SleepSessionRecord::class, //어떤 데이터인지 정한다
            timeRangeFilter = TimeRangeFilter.between(
                startTime.toInstant(),
                date.toInstant()
            ), //언제부터 언제까지 데이터를 가져올지 정한다
            ascendingOrder = false //내림차순 정렬하여 반환
        )
        val sleepSessions = healthConnectClient.readRecords(request)

        sleepSessions.records.forEach { session ->
            val sessionTimeFilter = TimeRangeFilter.between(session.startTime, session.endTime)
            val durationAggregateRequest = AggregateRequest(
                metrics = setOf(SleepSessionRecord.SLEEP_DURATION_TOTAL),
                timeRangeFilter = sessionTimeFilter
            )
            val aggregateResponse = healthConnectClient.aggregate(durationAggregateRequest)

            val zoneTime = session.endTime.atZone(ZoneId.systemDefault())
            val year = zoneTime.year
            val month = zoneTime.month
            val day = zoneTime.dayOfMonth
            if (year == standardTime.year && month == standardTime.month && day == standardTime.dayOfMonth) {
                Log.d(
                    TAG,
                    "readSleepDataOneDay: ${year} ${month} ${day} : ${aggregateResponse[SleepSessionRecord.SLEEP_DURATION_TOTAL]}"
                )
                sessions.add(
                    SleepSessionResponse(
                        uid = session.metadata.id,
                        title = session.title,
                        notes = session.notes,
                        startTime = session.startTime,
                        startZoneOffset = session.startZoneOffset,
                        endTime = session.endTime,
                        endZoneOffset = session.endZoneOffset,
                        duration = aggregateResponse[SleepSessionRecord.SLEEP_DURATION_TOTAL],
                        stages = session.stages
                    )
                )
            }
        }
        return sessions
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun readStepDataOneDay(date: ZonedDateTime): Long {
        var result = 0L
        var endTime = date
        if (date.hour >= 0 && date.hour <= 2) {
            endTime = endTime.minusDays(1).withHour(23).withMinute(59).withSecond(59)
        }
        val startTime = endTime.withHour(0)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)

        val request = ReadRecordsRequest(
            recordType = StepsRecord::class, //어떤 데이터인지 정한다
            timeRangeFilter = TimeRangeFilter.between(
                startTime.toInstant(),
                endTime.toInstant()
            ), //언제부터 언제까지 데이터를 가져올지 정한다
            ascendingOrder = false //내림차순 정렬하여 반환
        )

        val stepRecord = healthConnectClient.readRecords(request)

        Log.d(TAG, "readStepDataOneDay: ${stepRecord.records}")
        if (stepRecord.records.isEmpty()) {
            Log.d(TAG, "readStepDataOneDay: empty!")
            result = 0L
        } else {
            result = stepRecord.records[0].count
        }
        return result
    }
}
