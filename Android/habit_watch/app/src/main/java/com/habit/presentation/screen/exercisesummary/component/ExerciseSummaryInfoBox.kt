package com.habit.presentation.screen.exercisesummary.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.habit.R
import com.habit.data.model.ExerciseInfoType
import com.habit.presentation.common.FontStyles

private const val TAG = "ExerciseSummaryInfoBox"
@Composable
fun ExerciseSummaryInfoBox(
    exerciseInfoType: ExerciseInfoType,
    value: AnnotatedString
) {
    Log.d(TAG, "ExerciseSummaryInfoBox: ${exerciseInfoType} ${value}")
    val infoText = when (exerciseInfoType) {
        ExerciseInfoType.CALORIE -> {
            stringResource(id = R.string.exercise_summary_total_calorie) + " : ${value}"
        }

        ExerciseInfoType.TIME -> {
            stringResource(id = R.string.exercise_summary_total_time) + " : ${value}"
        }

        else -> {
            ""
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(id = exerciseInfoType.icon),
            contentDescription = exerciseInfoType.description
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = infoText, style = FontStyles.smallNormal)
    }
}

@Composable
@Preview
fun PrevExerciseSummaryInfoBox() {
    Box(modifier = Modifier.background(color = Color.Black)){
        ExerciseSummaryInfoBox(
            ExerciseInfoType.TIME,
            buildAnnotatedString { append("5.3") }
        )
    }

}

@Composable
@Preview
fun PrevExerciseSummaryInfoBox2() {
    Box(modifier = Modifier.background(color = Color.Black)){
        ExerciseSummaryInfoBox(
            ExerciseInfoType.CALORIE,
            buildAnnotatedString { append("5.3") }
        )
    }

}