package com.habit.ui.screen.home.component.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.habit.domain.model.mission.ExerciseDto
import com.habit.domain.model.mission.MissionDto
import com.habit.ui.common.model.FreeMissionType
import com.habit.ui.common.model.MissionStateType
import com.habit.ui.common.model.MissionType
import com.habit.ui.screen.home.component.bottomsheet.component.BottomSheetHeader
import com.habit.ui.screen.home.component.bottomsheet.component.CalorieBottomSheetBody
import java.util.Date

@Composable
fun CalorieBottomSheet(
    missionStateType: MissionStateType,
    totalCalorie: Int,
    exerciseList: List<ExerciseDto>,
    missionDto: MissionDto,
    onSuccessButtonClicked: () -> Unit
) {
    Column(modifier = Modifier.padding(20.dp)) {
        BottomSheetHeader(
            missionStateType = missionStateType,
            missionType = MissionType.CALORIE,
            calorie = totalCalorie,
            missionDto = missionDto,
            onSuccessButtonClicked = onSuccessButtonClicked
        )
        Spacer(modifier = Modifier.size(20.dp))
        CalorieBottomSheetBody(exerciseList)
        Spacer(modifier = Modifier.size(50.dp))
    }
}

@Composable
@Preview
fun PrevCalorieBottomSheet() {
    CalorieBottomSheet(
        MissionStateType.REWARD_POSSIBLE, 30, listOf<ExerciseDto>(
            ExerciseDto(0, 0, "walk", Date(), 10),
            ExerciseDto(0, 0, "sdf", Date(), 10),
            ExerciseDto(0, 0, "swim", Date(), 10),

            ),
        missionDto = MissionDto(0,30,4,30, FreeMissionType.BOOK.type,"토지 읽기")

    ) {}
}