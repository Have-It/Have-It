package com.habit.ui.screen.home.component.header

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.habit.R
import com.habit.domain.model.home.HomeDataDto
import com.habit.domain.model.home.MemberHomeInfoDto
import com.habit.ui.common.component.RoundBackTextWithIcon
import com.habit.ui.common.component.RoundBackTextWithIconClickable
import com.habit.ui.screen.home.SleepPermissionState
import com.habit.ui.theme.HabitPurpleNormal
import com.habit.ui.theme.HabitTheme

@Composable
fun HeaderComponent(
    permissionState: SleepPermissionState,
    memberHomeInfo: MemberHomeInfoDto,
    homeDataDto: HomeDataDto,
    stepCount: Long,
    permissions: Set<String>,
    permissionsLauncher: ManagedActivityResultLauncher<Set<String>, Set<String>>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.padding(10.dp, 10.dp)
    ) {
        Box(modifier = Modifier.weight(3f)){
            HeaderInfoBox(
                permissionState,
                memberHomeInfo.nickName,
                homeDataDto.coin,
                homeDataDto.totalSuccess,
                stepCount,
                permissions,
                permissionsLauncher
            )
        }

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(memberHomeInfo.profileImage)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.DISABLED)
                .memoryCachePolicy(CachePolicy.DISABLED)
                .build(),
            contentDescription = "user character",
            contentScale = ContentScale.Fit,
            modifier = Modifier.weight(2f),
            error = painterResource(id = R.drawable.icon_image_not_found)
        )
//        Image(
//            modifier = Modifier.weight(1f),
//            painter = painterResource(id = R.drawable.image_user_fortest),
//            contentDescription = "user character"
//        )
    }
}

@Composable
fun HeaderInfoBox(
    permissionState: SleepPermissionState,
    userNickName: String,
    coinValue: Int,
    consecutiveDay: Int,
    stepCount: Long,
    permissions: Set<String>,
    permissionsLauncher: ManagedActivityResultLauncher<Set<String>, Set<String>>
) {
    Column(
        modifier = Modifier.fillMaxHeight().padding(20.dp, 0.dp, 0.dp, 0.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column() {
            Text(text = "${userNickName}님", style = HabitTheme.typography.headTextPurpleNormal)
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = "오늘도 화이팅!", style = HabitTheme.typography.headTextPurpleNormal)
        }

        Column() {
            RoundBackTextWithIcon(
                backgroundColor = HabitPurpleNormal,
                text = coinValue.toString(),
                textStyle = HabitTheme.typography.mediumBoldTextWhite,
                icon = painterResource(id = R.drawable.icon_money_bag),
                contentDescription = "coin value",
                paddingVertical = 6.dp,
                paddingHorizontal = 24.dp
            )
            Spacer(modifier = Modifier.size(18.dp))
            RoundBackTextWithIcon(
                backgroundColor = HabitPurpleNormal,
                text = "연속달성 ${consecutiveDay}일",
                textStyle = HabitTheme.typography.mediumBoldTextWhite,
                icon = painterResource(id = R.drawable.image_thumbs_up),
                contentDescription = "consecutive day",
                paddingVertical = 6.dp,
                paddingHorizontal = 18.dp
            )
            Spacer(modifier = Modifier.size(18.dp))
            if(permissionState == SleepPermissionState.PermissionAccepted){
                RoundBackTextWithIcon(
                    backgroundColor = HabitPurpleNormal,
                    text = "${stepCount}걸음",
                    textStyle = HabitTheme.typography.mediumBoldTextWhite,
                    icon = painterResource(id = R.drawable.icon_running_shoe),
                    contentDescription = "consecutive day",
                    paddingVertical = 6.dp,
                    paddingHorizontal = 24.dp
                )
            }else{
                RoundBackTextWithIconClickable(
                    backgroundColor = HabitPurpleNormal,
                    text = "걸음수 권한이 필요합니다",
                    textStyle = HabitTheme.typography.mediumBoldTextWhite,
                    icon = painterResource(id = R.drawable.icon_warning),
                    contentDescription = "consecutive day",
                    paddingVertical = 6.dp,
                    paddingHorizontal = 24.dp
                ){
                    permissionsLauncher.launch(permissions)
                }
            }

        }

    }
}

//@Composable
//@Preview
//fun PrevHeaderComponent() {
//    HeaderComponent(
//        SleepPermissionState.PermissionAccepted,
//        MemberHomeInfoDto("닉네임", ""),
//        HomeDataDto(10, 12, true, true, true),
//        1000L
//    )
//}