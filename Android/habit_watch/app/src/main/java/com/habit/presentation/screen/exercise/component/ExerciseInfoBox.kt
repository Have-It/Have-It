package com.habit.presentation.screen.exercise.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.health.composables.ActiveDurationText
import com.habit.data.model.ExerciseInfoType
import com.habit.presentation.common.FontStyles
import com.habit.presentation.screen.exercise.ExerciseScreenState
import com.habit.presentation.theme.HabitBlueLight
import com.habit.util.formatCalories
import com.habit.util.formatElapsedTime


@OptIn(ExperimentalHorologistApi::class)
@Composable
fun ExerciseInfoBox(
    exerciseInfoType: ExerciseInfoType,
    uiState: ExerciseScreenState
) {
    val lastActiveDurationCheckpoint = uiState.exerciseState?.activeDurationCheckpoint
    val exerciseState = uiState.exerciseState?.exerciseState

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            modifier = Modifier.size(22.dp),
            tint = HabitBlueLight,
            painter = painterResource(id = exerciseInfoType.icon),
            contentDescription = exerciseInfoType.description
        )
        if (exerciseInfoType == ExerciseInfoType.CALORIE) {
            if (uiState.exerciseState?.exerciseMetrics?.calories != null)
                Text(
                    text = formatCalories(uiState.exerciseState?.exerciseMetrics?.calories),
                    style = FontStyles.smallNormal.copy(color = HabitBlueLight)
                )
            else
                Text(
                    text = "--",
                    style = FontStyles.smallNormal.copy(color = HabitBlueLight)
                )
        } else if (exerciseInfoType == ExerciseInfoType.HEART_REATE) {
            Text(
                text = "${uiState.exerciseState?.exerciseMetrics?.heartRate ?: "--"}",
                style = FontStyles.smallNormal.copy(color = HabitBlueLight)
            )
        } else {
            if (exerciseState != null && lastActiveDurationCheckpoint != null) {
                ActiveDurationText(
                    checkpoint = lastActiveDurationCheckpoint,
                    state = uiState.exerciseState.exerciseState
                ) {
                    Text(
                        text = formatElapsedTime(it, includeSeconds = true),
                        style = FontStyles.smallNormal.copy(color = HabitBlueLight)
                    )
                }
            } else {
                Text(text = "--", style = FontStyles.smallNormal.copy(color = HabitBlueLight))
            }
        }
    }
}

@Composable
@Preview
fun PrevExerciseInfoBox() {
   // ExerciseInfoBox(exerciseInfoType = ExerciseInfoType.CALORIE, )
}