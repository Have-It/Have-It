package com.habit.ui.screen.statistic.component

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.habit.R
import com.habit.domain.model.statistics.Date
import com.habit.ui.theme.HabitTheme
import com.habit.util.rememberFirstMostVisibleMonth
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

private const val TAG = "Calendar_싸피"
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SimpleHorizontalCalendar(dateMap: Map<Date, Int>) {

    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library
    val daysOfWeek = remember { daysOfWeek() }

    val colorMap: Map<Int, Color> = mapOf(
        0 to HabitTheme.colors.habitWhite,
        1 to HabitTheme.colors.habitPurpleExtralight,
        2 to HabitTheme.colors.habitPurpleLight,
        3 to HabitTheme.colors.habitPurpleNormal,
    )

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )
    val coroutineScope = rememberCoroutineScope()
    val visibleMonth = rememberFirstMostVisibleMonth(state, viewportPercent = 90f)

    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier.background(
                HabitTheme.colors.habitPurpleExtralight2
            ),
        ) {
            SimpleCalendarTitle(
                modifier = Modifier.padding(vertical = 8.dp),
                currentMonth = visibleMonth.yearMonth,
                goToPrevious = {
                    coroutineScope.launch {
                        state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                    }
                },
                goToNext = {
                    coroutineScope.launch {
                        state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                    }
                },
            )
            HorizontalCalendar(

                monthHeader = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        DaysOfWeekTitle(daysOfWeek = daysOfWeek)
                    }

                },
                monthBody = { _, content ->

                    Box(
//                    modifier = Modifier.background(
//                        color = HabitTheme.colors.habitPurpleExtralight2
////                        brush = Brush.verticalGradient(
////                            colors = listOf(
////                                Color(0xFFB2EBF2),
////                                Color(0xFFB2B8F2)
////                            )
////                        )
//                    )
                    ) {
                        content() // Render the provided content!
                    }
                },
                state = state,
                dayContent = { Day(it, getDateColor(it, dateMap, colorMap)) },
            )
        }

    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun getDateColor(date: CalendarDay, dateMap: Map<Date, Int>, colorMap: Map<Int, Color>): Color {
    val missionCnt = dateMap.get(Date(date.date.year, date.date.monthValue, date.date.dayOfMonth))
    return colorMap.get(missionCnt) ?: HabitTheme.colors.habitWhite
}

/**
 * 각각의 날짜입니다.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Day(day: CalendarDay, dayColor: Color) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable {
                getDateInfo(day)
            }, // This is important for square sizing!
        contentAlignment = Alignment.Center
    ) {

        Surface(
            shape = RoundedCornerShape(32.dp),
            color = dayColor,
            modifier = Modifier
                .height(32.dp)
                .width(32.dp),

            ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = day.date.dayOfMonth.toString(),
                    color = if (day.position == DayPosition.MonthDate) Color.Black else Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * 월~일까지의 일주일입니다.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREA),
            )
        }
    }
}


//@RequiresApi(Build.VERSION_CODES.O)
//@Preview
//@Composable
//fun Preview() {
//    SimpleHorizontalCalendar(: Map<Date, Int>)
//}


/**
 * 달력제목
 * 화살표를 통해 날짜를 이동할 수 있습니다.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SimpleCalendarTitle(
    modifier: Modifier,
    currentMonth: YearMonth,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
) {
    Row(
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CalendarNavigationIcon(
            icon = painterResource(id = R.drawable.icon_left_arrow_gray05),

            contentDescription = "Previous",
            onClick = goToPrevious,
        )
        Text(
            textAlign = TextAlign.Center,
            text = "${currentMonth.year}년 ${currentMonth.monthValue}월",
            style = HabitTheme.typography.headline2,
            color = HabitTheme.colors.gray600
        )
        CalendarNavigationIcon(
            icon = painterResource(id = R.drawable.icon_right_arrow_gray05),
            contentDescription = "Next",
            onClick = goToNext,
        )
    }
}

/**
 * 달력 좌,우 화살표 아이콘
 */
@Composable
private fun CalendarNavigationIcon(
    icon: Painter,
    contentDescription: String,
    onClick: () -> Unit,
) = Box(
    modifier = Modifier
        .fillMaxHeight()
        .aspectRatio(1f)
        .clip(shape = CircleShape)
        .clickable(role = Role.Button, onClick = onClick),
) {
    Icon(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .align(Alignment.Center),
        painter = icon,
        contentDescription = contentDescription,
        tint = HabitTheme.colors.gray600
    )
}

/**
 * 날짜를 눌렀을 때 수행할 로직입니다.
 */
fun getDateInfo(day: CalendarDay) {

}

/**
 * 날짜에 대해서 성공한 횟수를 가져오는 로직입니다.
 */
fun getDateMissionCnt() {
}





