package com.habit.ui.screen.home.component.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.habit.domain.model.mission.MissionDto
import com.habit.ui.common.model.MissionStateType
import com.habit.ui.common.model.MissionType
import com.habit.ui.screen.home.component.bottomsheet.component.BottomSheetHeader
import com.habit.ui.screen.home.component.bottomsheet.component.FreeBottomSheetBody
import com.habit.util.stringToFreeMissionType

@Composable
fun FreeBottomSheet(
    missionStateType: MissionStateType,
    missionData: MissionDto,
    onSuccessButtonClicked: () -> Unit
) {
    Column(modifier = Modifier.padding(20.dp)) {
        BottomSheetHeader(
            missionStateType = missionStateType,
            missionType = MissionType.FREE,
            freeMissionType = stringToFreeMissionType(missionData.freeMissionType),
            freeMissionDetail = missionData.freeMissionDetail,
            missionDto = missionData,
            onSuccessButtonClicked = onSuccessButtonClicked
        )

        Spacer(modifier = Modifier.size(100.dp))
    }
}

@Composable
@Preview
fun PrevFreeBottomSheet() {

    FreeBottomSheet(
        MissionStateType.REWARD_POSSIBLE, MissionDto(0, 23, 1, 23, "book", "토지읽기"),
    ) {}
}