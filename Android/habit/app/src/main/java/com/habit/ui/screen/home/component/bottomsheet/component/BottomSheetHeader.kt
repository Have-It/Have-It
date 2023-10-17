package com.habit.ui.screen.home.component.bottomsheet.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.habit.R
import com.habit.domain.model.mission.MissionDto
import com.habit.ui.common.model.FreeMissionType
import com.habit.ui.common.model.MissionStateType
import com.habit.ui.common.model.MissionType
import com.habit.ui.theme.HabitPurpleNormal
import com.habit.ui.theme.HabitTheme
import com.habit.util.typeToIcon

@Composable
fun BottomSheetHeader(
    missionStateType: MissionStateType,
    missionType: MissionType,
    freeMissionType: FreeMissionType = FreeMissionType.BOOK,
    calorie: Int = 0,
    sleepHour: Long = 0,
    sleepMinute: Long = 0,
    freeMissionDetail: String = "",
    missionDto: MissionDto,
    onSuccessButtonClicked: () -> Unit
) {
    var alreadyRewardedMessage: Int = R.string.home_already_coin_achieved
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BottomSheetMissionInfo(
            missionType,
            freeMissionType,
            calorie,
            sleepHour,
            sleepMinute,
            missionDto,
            freeMissionDetail
        )
        Spacer(modifier = Modifier.size(20.dp))

        if (missionType == MissionType.FREE) {
            androidx.compose.material.Text(
                text = stringResource(id = R.string.home_bottom_sheet_free_check_if_you_done),
                style = HabitTheme.typography.headTextPurpleNormal
            )
            Spacer(modifier = Modifier.size(20.dp))
            Image(
                modifier = Modifier.size(250.dp),
                painter = painterResource(id = R.drawable.image_pets),
                contentDescription = "check if you done"
            )
            Spacer(modifier = Modifier.size(20.dp))
        }

        if (missionStateType == MissionStateType.REWARD_POSSIBLE) {
            GetCoinButton(missionType, onSuccessButtonClicked)
        } else if (missionStateType == MissionStateType.ALREADY_REWARDED) {
            Box(
                modifier = Modifier
                    .padding(5.dp, 10.dp)
            ) {
                if(missionType == MissionType.FREE){
                    alreadyRewardedMessage = R.string.home_already_pet_reward_achieved
                }
                Text(
                    text = stringResource(id = alreadyRewardedMessage),
                    style = HabitTheme.typography.headTextPurpleNormal,
                    modifier = Modifier.padding(20.dp, 4.dp)
                )
            }
        } else if (missionStateType == MissionStateType.NOT_ACHIEVED) {
            Box(
                modifier = Modifier
                    .padding(5.dp, 10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.home_not_achieved),
                    style = HabitTheme.typography.headTextPurpleNormal,
                    modifier = Modifier.padding(20.dp, 4.dp)
                )
            }
        }

    }
}

@Composable
fun BottomSheetMissionInfo(
    missionType: MissionType,
    freeMissionType: FreeMissionType?,
    calorie: Int = 0,
    sleepHour: Long = 0,
    sleepMinute: Long = 0,
    missionDto: MissionDto,
    freeMissionDetail: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        var missionImage: Painter = painterResource(id = R.drawable.icon_party_face)
        var missionTitle: String = stringResource(id = R.string.home_bottom_sheet_calorie_title)

        when (missionType) {
            MissionType.CALORIE -> {
                missionImage = painterResource(id = R.drawable.icon_party_face)
                missionTitle = stringResource(id = R.string.home_bottom_sheet_calorie_title)
            }

            MissionType.SLEEP -> {
                missionImage = painterResource(id = R.drawable.icon_moon)
                missionTitle = stringResource(id = R.string.home_bottom_sheet_sleep_title)
            }

            else -> {
                missionTitle = stringResource(id = R.string.home_bottom_sheet_free_title)
                missionImage = painterResource(
                    id = typeToIcon(
                        freeMissionType?.type ?: FreeMissionType.BOOK.type
                    )
                )

            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = missionTitle,
                style = HabitTheme.typography.headTextPurpleNormal
            )
            Spacer(modifier = Modifier.size(15.dp))
            BottomSheetMissionInfoDetailText(
                missionType = missionType,
                calorie = calorie,
                sleepHour = sleepHour,
                sleepMinute = sleepMinute,
                freeMissionDetail = freeMissionDetail,
                missionDto = missionDto
            )
        }
        Box() {
            Image(
                modifier = Modifier.size(98.dp),
                contentScale = ContentScale.Fit,
                painter = missionImage,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun BottomSheetMissionInfoDetailText(
    missionType: MissionType,
    calorie: Int = 0,
    sleepHour: Long = 0,
    sleepMinute: Long = 0,
    freeMissionDetail: String = "",
    missionDto: MissionDto
) {
    when (missionType) {
        MissionType.CALORIE -> {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = calorie.toString(),
                        style = HabitTheme.typography.extraLargeBoldTextPurpleNormal
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = stringResource(id = R.string.home_bottom_sheet_kcal),
                        style = HabitTheme.typography.headTextPurpleNormal
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "목표: ${missionDto.calorie}kcal",
                    style = HabitTheme.typography.headTextPurpleNormal
                )
            }

        }

        MissionType.SLEEP -> {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = sleepHour.toString(),
                        style = HabitTheme.typography.extraLargeBoldTextPurpleNormal
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = stringResource(id = R.string.home_bottom_sheet_hour),
                        style = HabitTheme.typography.headTextPurpleNormal
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = sleepMinute.toString(),
                        style = HabitTheme.typography.extraLargeBoldTextPurpleNormal
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = stringResource(id = R.string.home_bottom_sheet_min),
                        style = HabitTheme.typography.headTextPurpleNormal
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "목표: ${missionDto.hour}시간 ${missionDto.minute}분",
                    style = HabitTheme.typography.headTextPurpleNormal
                )
            }
        }

        else -> {
            Text(
                text = freeMissionDetail,
                style = HabitTheme.typography.headTextPurpleNormal,
                softWrap = true
            )
        }
    }
}

@Composable
@Preview
fun PrevBottomSheetHeader1() {
    Column {
        BottomSheetHeader(
            missionStateType = MissionStateType.NOT_ACHIEVED,
            missionType = MissionType.CALORIE,
            calorie = 350,
            missionDto = MissionDto(0, 30, 4, 30, FreeMissionType.BOOK.type, "토지 읽기")
        ) {}
    }
}

@Composable
@Preview
fun PrevBottomSheetHeader2() {
    BottomSheetHeader(
        missionStateType = MissionStateType.ALREADY_REWARDED,
        missionType = MissionType.SLEEP,
        sleepHour = 4,
        sleepMinute = 50,
        missionDto = MissionDto(0, 30, 4, 30, FreeMissionType.BOOK.type, "토지 읽기")

    ) {}
}

@Composable
@Preview
fun PrevBottomSheetHeader3() {

    BottomSheetHeader(
        missionStateType = MissionStateType.ALREADY_REWARDED,
        missionType = MissionType.FREE,
        freeMissionType = FreeMissionType.DIARY,
        freeMissionDetail = "일기 작성하기",
        missionDto = MissionDto(0, 30, 4, 30, FreeMissionType.BOOK.type, "토지 읽기")

    ) {}
}

@Composable
@Preview
fun PrevBottomSheetHeader4() {
    BottomSheetHeader(
        missionStateType = MissionStateType.ALREADY_REWARDED,
        missionType = MissionType.FREE,
        freeMissionType = FreeMissionType.VITAMIN,
        freeMissionDetail = "일기 작성하기",
        missionDto = MissionDto(0,30,4,30,FreeMissionType.BOOK.type,"토지 읽기")
    ) {}
}

@Composable
@Preview
fun PrevBottomSheetHeader5() {
    BottomSheetHeader(
        missionStateType = MissionStateType.ALREADY_REWARDED,
        missionType = MissionType.FREE,
        freeMissionType = FreeMissionType.STUDY,
        freeMissionDetail = "수학 5문제, 영어 단어 10개 암기",
        missionDto = MissionDto(0,30,4,30,FreeMissionType.BOOK.type,"토지 읽기")

    ) {}
}

@Composable
@Preview
fun PrevBottomSheetHeader6() {
    BottomSheetHeader(
        missionStateType = MissionStateType.ALREADY_REWARDED,
        missionType = MissionType.FREE,
        freeMissionType = FreeMissionType.DIET,
        freeMissionDetail = "일기 작성하기",
        missionDto = MissionDto(0,30,4,30,FreeMissionType.BOOK.type,"토지 읽기")

    ) {}
}

@Composable
@Preview
fun PrevBottomSheetHeader7() {
    BottomSheetHeader(
        missionStateType = MissionStateType.ALREADY_REWARDED,
        missionType = MissionType.FREE,
        freeMissionType = FreeMissionType.CLEANING,
        freeMissionDetail = "일기 작성하기",
        missionDto = MissionDto(0,30,4,30,FreeMissionType.BOOK.type,"토지 읽기")

    ) {}
}

@Composable
@Preview
fun PrevBottomSheetHeader8() {
    BottomSheetHeader(
        missionStateType = MissionStateType.ALREADY_REWARDED,
        missionType = MissionType.FREE,
        freeMissionType = FreeMissionType.WATER,
        freeMissionDetail = "일기 작성하기",
        missionDto = MissionDto(0,30,4,30,FreeMissionType.BOOK.type,"토지 읽기")

    ) {}
}
