package com.habit.presentation.screen.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.habit.R
import com.habit.data.model.ExerciseInfoType
import com.habit.presentation.common.FontStyles
import com.habit.presentation.navigation.Screen
import com.habit.presentation.screen.MainViewModel
import com.habit.presentation.screen.exercise.component.ExerciseInfoBox
import com.habit.presentation.screen.loading.LoadingScreen
import com.habit.presentation.theme.HabitBlueGray

@Composable
fun ExerciseScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    //   viewModel: ExerciseViewModel
) {
    val viewModel = hiltViewModel<ExerciseViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedExcercise by mainViewModel.selectedExcercise.collectAsState()
    var buttonState by remember { mutableStateOf(true) }

    if (uiState.isEnded) {
        SideEffect {
            mainViewModel.setSummary(uiState.toSummary())
            navController.navigate(Screen.ExerciseSummaryNav.route) {
                popUpTo(Screen.ExerciseNav.route) {
                    inclusive = true
                }
            }
        }
    } else if (uiState.isActive == true) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp, 20.dp)
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                    ExerciseInfoBox(ExerciseInfoType.CALORIE, uiState)
                }
                Image(
                    modifier = Modifier
                        .size(70.dp)
                        .weight(1f),
                    painter = painterResource(id = selectedExcercise.icon),
                    contentDescription = selectedExcercise.excerciseName
                )
                Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                    ExerciseInfoBox(ExerciseInfoType.HEART_REATE, uiState)
                }
            }
            ExerciseInfoBox(ExerciseInfoType.TIME, uiState)
            Button(
                modifier = Modifier.size(30.dp),
                onClick = {
                    buttonState = false
                    viewModel.endExercise()
                },
                enabled = buttonState,
                colors = ButtonDefaults.buttonColors(backgroundColor = HabitBlueGray)
            ) {
                Icon(
                    tint = Color.Black,
                    painter = painterResource(id = R.drawable.icon_stop),
                    contentDescription = stringResource(id = R.string.exercise_stop_button_description)
                )
            }
        }
    } else if (uiState.isActive == false) {

        LoadingScreen()

    }


}
//
//@Preview(
//    device = "id:wearos_large_round",
//    backgroundColor = 4278190080,
//    showBackground = true,
//    group = "Devices - Large Round",
//    showSystemUi = true
//)
//@Composable
//fun PrevExerciseScreen() {
//    val mainViewModel: MainViewModel = viewModel()
//    val navController = rememberSwipeDismissableNavController()
//    ExerciseScreen(navController, mainViewModel)
//}