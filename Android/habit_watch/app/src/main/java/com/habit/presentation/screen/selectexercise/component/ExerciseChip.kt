package com.habit.presentation.screen.selectexercise.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.health.services.client.proto.DataProto.ExerciseType
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.habit.data.model.HabitExerciseType
import com.habit.R

@Composable
fun ExerciseChip(
    exerciseType: HabitExerciseType,
    onClick: () -> Unit
) {
    Chip(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        label = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = exerciseType.icon),
                    contentDescription = exerciseType.excerciseName
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = exerciseType.excerciseName)
            }

        },
        onClick = { onClick() },
    )
}

@Composable
@Preview
fun PrevExerciseChip() {
    Column() {
        ExerciseChip(exerciseType = HabitExerciseType.HIKING) {}
        ExerciseChip(exerciseType = HabitExerciseType.BADMINTON) {}
        ExerciseChip(exerciseType = HabitExerciseType.DANCE) {}
    }

}