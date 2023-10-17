package com.habit.ui.screen.home.component.bottomsheet.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.habit.R
import com.habit.ui.common.model.MissionType
import com.habit.ui.theme.HabitTheme

@Composable
fun FreeBottomSheetBody(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.home_bottom_sheet_free_check_if_you_done),
            style = HabitTheme.typography.headTextPurpleNormal
        )
        Spacer(modifier = Modifier.size(20.dp))
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.image_clap),
            contentDescription = "check if you done"
        )
        Spacer(modifier = Modifier.size(20.dp))
        GetCoinButton(MissionType.FREE) {
            onClick()
        }
    }
}

@Composable
@Preview
fun PrevFreeBottomSheetBody() {
    FreeBottomSheetBody({})
}