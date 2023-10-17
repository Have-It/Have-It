package com.habit.ui.screen.statistic

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.habit.R
import com.habit.domain.model.statistics.Date
import com.habit.domain.model.statistics.DateMissionCnt
import com.habit.ui.screen.example.ExampleViewModel
import com.habit.ui.screen.statistic.component.CalendarDots
import com.habit.ui.screen.statistic.component.MyBarChartParent
import com.habit.ui.screen.statistic.component.SimpleHorizontalCalendar
import com.habit.ui.theme.HabitTheme


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatisticScreen(
    navController: NavHostController,
    viewModel: StatisticViewModel = hiltViewModel()
) {

    val dateMissionCntList by viewModel.dateAndMissionCnt.collectAsState()

    LaunchedEffect(true) {
        viewModel.getDateSuccessInfo()
    }


    Column(
        Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        CalendarDots()
        Spacer(modifier = Modifier.size(8.dp))
        SimpleHorizontalCalendar(transferToMap(dateMissionCntList))
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            stringResource(R.string.statistic_calorie),
            style = HabitTheme.typography.headline3,
            color = HabitTheme.colors.habitPurpleNormal
        )
        Spacer(modifier = Modifier.size(8.dp))
        MyBarChartParent()
    }


}

fun transferToMap(dateMissionCntList: List<DateMissionCnt>): Map<Date, Int> {
    val dateMap = mutableMapOf<Date, Int>()
    for (element in dateMissionCntList) {
        dateMap.put(Date(element.year, element.month, element.day), element.missionCnt)
    }
    return dateMap
}
