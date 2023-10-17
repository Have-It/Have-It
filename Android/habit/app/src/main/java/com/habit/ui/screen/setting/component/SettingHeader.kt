package com.habit.ui.screen.setting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.habit.R
import com.habit.domain.model.user.MemberSettingPageInfoDto
import com.habit.domain.model.user.StatDto
import com.habit.ui.common.component.MediumRoundButton
import com.habit.ui.common.component.SquareBoxRoundedCorner
import com.habit.ui.theme.HabitPurpleExtraLight
import com.habit.ui.theme.HabitPurpleLight
import com.habit.ui.theme.HabitPurpleNormal
import com.habit.ui.theme.HabitTheme
import com.habit.util.intToStatString

@Composable
fun SettingHeader(
    memberSettingPageInfo: MemberSettingPageInfoDto,
    stat: StatDto,
    id: Int
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            modifier = Modifier
                .background(
                    color = HabitPurpleExtraLight,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(memberSettingPageInfo.settingPageProfileImage)
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.DISABLED)
                    .memoryCachePolicy(CachePolicy.DISABLED)
                    .build(),
                contentDescription = "${memberSettingPageInfo.settingPageProfileImage}+${id}",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(100.dp),
                error = painterResource(id = R.drawable.icon_image_not_found)
            )
//            Image(
//                modifier = Modifier.size(100.dp),
//                painter = painterResource(id = R.drawable.image_setting_profile_test),
//                contentDescription = "user profile"
//            )
        }
        Spacer(modifier = Modifier.size(14.dp))
        Text(
            text = memberSettingPageInfo.nickName,
            style = HabitTheme.typography.headTextPurpleNormal
        )
        Spacer(modifier = Modifier.size(14.dp))
        MediumRoundButton(
            text = stringResource(id = R.string.setting_modify_user_info),
            color = HabitPurpleLight,
            textStyle = HabitTheme.typography.mediumBoldTextWhite,
        ) {

        }
        Spacer(modifier = Modifier.size(14.dp))
        SquareBoxRoundedCorner(backgroundColor = HabitPurpleNormal, paddingVertical = 10.dp) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.setting_user_weight),
                        style = HabitTheme.typography.mediumBoldTextWhite
                    )
                    Text(
                        text = intToStatString(stat.characterWeight),
                        //text = stringResource(id = R.string.setting_user_weight_value, 50),
                        style = HabitTheme.typography.miniBoldBody.copy(color = Color.White)
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                Box(
                    modifier = Modifier
                        .size(1.dp, 40.dp)
                        .background(color = Color.White)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.setting_user_dark),
                        style = HabitTheme.typography.mediumBoldTextWhite
                    )
                    Text(
                        //text = "50" + "%",
                        text = intToStatString(stat.characterDarkcircle),
                        style = HabitTheme.typography.miniBoldBody.copy(color = Color.White)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PrevSettingHeader() {
    SettingHeader(MemberSettingPageInfoDto("닉네임", ""), StatDto(12, 12),0)
}