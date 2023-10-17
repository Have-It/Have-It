package com.habit.presentation.screen.startexercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.habit.R
import com.habit.data.model.HabitExerciseType
import com.habit.presentation.common.FontStyles
import com.habit.presentation.navigation.Screen
import com.habit.presentation.screen.ExerciseCapabilityState
import com.habit.presentation.screen.MainViewModel
import com.habit.presentation.theme.HabitBlueGray


@Composable
fun StartExerciseScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val viewModel = hiltViewModel<StartExerciseViewModel>()
    val selectedExercise by mainViewModel.selectedExcercise.collectAsState()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val exerciseCapability by mainViewModel.exerciseCapability.collectAsState()
    SideEffect {
        val preparingState = uiState
        if (preparingState is StartExerciseState.Preparing && exerciseCapability == ExerciseCapabilityState.NotAvailable) {
            navController.navigate(Screen.ExerciseNotAvailable.route) {
                popUpTo(navController.graph.id) {
                    inclusive = false
                }
            }
        }
    }
    if(exerciseCapability == ExerciseCapabilityState.Available){
        Scaffold() {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(id = R.string.start_exercise_title),
                    style = FontStyles.smallNormal
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = selectedExercise.icon),
                        contentDescription = selectedExercise.excerciseName
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = selectedExercise.excerciseName, style = FontStyles.largeNormal)
                }
                Button(
                    onClick = {
                        viewModel.startExercise(selectedExercise)
                        navController.navigate(Screen.ExerciseNav.route) {
                            popUpTo(Screen.SelectExerciseNav.route) {
                                inclusive = true
                            }
                        }
                    },
                    enabled = uiState is StartExerciseState.Preparing,
                    colors = ButtonDefaults.buttonColors(backgroundColor = HabitBlueGray)
                ) {
                    Icon(
                        tint = Color.Black,
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = stringResource(id = R.string.start_exercise_button_description),
                    )
                }
            }
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
fun PrevStartExerciseScreen() {
    val startExerciseViewModel: StartExerciseViewModel = viewModel()
    val mainViewModel: MainViewModel = viewModel()
    val navController = rememberSwipeDismissableNavController()
    StartExerciseScreen(navController, mainViewModel)
}