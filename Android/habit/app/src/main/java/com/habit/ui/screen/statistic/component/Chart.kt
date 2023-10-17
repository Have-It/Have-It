package com.habit.ui.screen.statistic.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.common.ChartDataCollection
import com.himanshoe.charty.common.config.ChartDefaults
import com.himanshoe.charty.line.LineChart
import com.himanshoe.charty.line.config.LineChartDefaults
import com.himanshoe.charty.line.model.LineData

//@Composable
//@Preview
//fun MyChart() {
//    fun BarChart(
//        barChartData = BarChartData(
//            bars = listOf(
//                BarChartData.Bar(
//                    label = "Bar Label",
//                    value = 100f,
//                    color = Color.Red
//                )
//            ),
//            // Optional properties.
//            modifier = Modifier.fillMaxSize(),
//            animation = simpleChartAnimation(),
//            barDrawer = SimpleBarDrawer(),
//            xAxisDrawer = SimpleXAxisDrawer(),
//            yAxisDrawer = SimpleYAxisDrawer(),
//            labelDrawer = SimpleValueDrawer()
//        )
//
//}

@Composable
@Preview
fun MyBarChartParent() {
//    BarChart(
//        barChartData =
//        BarChartData(
//            bars = listOf(
//                BarChartData.Bar(label = "Bar Label", value = 100f, color = Color.Red)
//
//            ),
//            startAtZero = true
//        ),
//        modifier = Modifier.fillMaxSize(),
//        animation = simpleChartAnimation(),
//        barDrawer = SimpleBarDrawer(),
//
//        xAxisDrawer = SimpleXAxisDrawer(),
//        yAxisDrawer = SimpleYAxisDrawer(),
//        labelDrawer = SimpleValueDrawer(),
//    )

//
//    BarChart(
//        dataCollection = ChartDataCollection(
//            listOf(
//                LineData(100f, "8/24"),
//                LineData(100f, "8/24"),
//                LineData(100f, "8/24"),
//                LineData(100f, "8/24"),
//                LineData(100f, "8/24"),
//                LineData(100f, "8/24"),
//                LineData(100f, "8/24"),
//                LineData(100f, "8/24"),
//                LineData(100f, "8/24"),
//                LineData(100f, "8/24"),
//                LineData(100f, "8/24"),
//                LineData(100f, "8/24"),
//                LineData(100f, "8/24"),
//                LineData(100f, "8/24"),
//                LineData(100f, "8/24"),
//
//            )
//        ),
//        modifier = Modifier.fillMaxSize(),
//        barSpacing = 8.dp,
//        padding = 16.dp,
//        barColor = HabitTheme.colors.habitPink,
//        axisConfig = ChartDefaults.axisConfigDefaults(),
//    )

//    @Composable
     LineChart(
        dataCollection = ChartDataCollection(
            listOf(
                LineData(200f, "8/1"),
                LineData(300f, "8/2"),
                LineData(100f, "8/3"),
                LineData(50f, "8/4"),
                LineData(100f, "8/5"),
                LineData(40f, "8/6"),
                LineData(60f, "8/7"),
                LineData(80f, "8/8"),
                LineData(100f, "8/9"),
                LineData(100f, "8/10"),
                LineData(130f, "8/11"),
                LineData(200f, "8/12"),
                LineData(150f, "8/13"),
                LineData(10f, "8/14"),
                LineData(10f, "8/15"),
                )),
        modifier  = Modifier,
        padding  = 16.dp,
        axisConfig = ChartDefaults.axisConfigDefaults(),
        radiusScale = 0.02f,
        lineConfig = LineChartDefaults.defaultConfig(),
        chartColors = LineChartDefaults.defaultColor(),
    )
}
