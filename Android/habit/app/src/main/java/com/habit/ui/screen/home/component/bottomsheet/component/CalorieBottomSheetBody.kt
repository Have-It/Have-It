package com.habit.ui.screen.home.component.bottomsheet.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.habit.R
import com.habit.domain.model.mission.ExerciseDto
import com.habit.ui.common.component.SquareBoxRoundedCorner
import com.habit.ui.common.model.HabitExerciseType
import com.habit.ui.theme.Gray900
import com.habit.ui.theme.HabitPurpleExtraLight3
import com.habit.ui.theme.HabitTheme
import com.habit.util.stringToExerciseType

@Composable
fun CalorieBottomSheetBody(
    exerciseList: List<ExerciseDto>
) {
    LazyColumn(modifier = Modifier.height(350.dp)){
        itemsIndexed(exerciseList){index, item ->
            val exercise = stringToExerciseType(item.exerciseType)
            CalorieCard(exerciseType = exercise, calorie = item.calorie)
            Spacer(modifier = Modifier.size(30.dp))
        }
    }

}

@Composable
fun CalorieCard(
    exerciseType: HabitExerciseType,
    calorie: Int
) {
    SquareBoxRoundedCorner(
        backgroundColor = HabitPurpleExtraLight3,
        paddingHorizontal = 20.dp,
        paddingVertical = 30.dp
    ) {
        Row {
            Row(modifier = Modifier.weight(4f), horizontalArrangement = Arrangement.SpaceAround) {
                Image(
                    painter = painterResource(id = exerciseType.icon),
                    contentDescription = exerciseType.cardText,
                    colorFilter = ColorFilter.tint(Gray900)
                )
                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = exerciseType.cardText,
                    style = HabitTheme.typography.cardTextBlack
                )
                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = calorie.toString(),
                    style = HabitTheme.typography.largeCardTextGray900
                )
            }
            Text(
                text = stringResource(id = R.string.home_bottom_sheet_kcal),
                modifier = Modifier.weight(2f),
                style = HabitTheme.typography.largeCardTextGray900,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview
fun PrevCalorieCard() {
    CalorieCard(exerciseType = HabitExerciseType.HIKING, 300)
}