package com.habit.presentation.screen.exercisenotavailable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.dialog.Confirmation
import com.google.android.horologist.compose.tools.WearPreviewDevices
import com.habit.R

@Composable
fun ExerciseNotAvailableScreen() {
    Confirmation(
    onTimeout = {}
    ) {
        Row(horizontalArrangement = Arrangement.Center) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = stringResource(id = R.string.not_avail),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row(horizontalArrangement = Arrangement.Center) {
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.not_avail)
            )
        }
    }
}


@Preview(
    device = "id:wearos_large_round",
    backgroundColor = 4278190080,
    showBackground = true,
    group = "Devices - Large Round",
    showSystemUi = true
)
@Composable
fun ExerciseNotAvailablePreview() {

        ExerciseNotAvailableScreen()

}
