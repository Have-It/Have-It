package com.habit.presentation.main

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.composable
import com.google.android.horologist.compose.navscaffold.WearNavScaffold
import com.habit.app.DataLayerViewModel
import com.habit.presentation.navigation.Screen
import com.habit.presentation.screen.MainViewModel
import com.habit.presentation.screen.exercise.ExerciseScreen
import com.habit.presentation.screen.exercise.ExerciseViewModel
import com.habit.presentation.screen.exercisenotavailable.ExerciseNotAvailableScreen
import com.habit.presentation.screen.exercisesummary.ExerciseSummaryScreen
import com.habit.presentation.screen.selectexercise.SelectExerciseScreen
import com.habit.presentation.screen.selectexercise.SelectExerciseViewModel
import com.habit.presentation.screen.startexercise.StartExerciseScreen
import com.habit.presentation.screen.startexercise.StartExerciseViewModel

@Composable
fun HabitApp(
    dataLayerViewModel: DataLayerViewModel,
    navController: NavHostController,
    onFinishActivity: () -> Unit,
) {
    val mainViewModel: MainViewModel = viewModel()
    val selectExerciseViewModel: SelectExerciseViewModel = viewModel()

    WearNavScaffold(
        navController = navController,
        startDestination = Screen.SelectExerciseNav.route
    ) {
        composable(Screen.SelectExerciseNav.route) {
            SelectExerciseScreen(navController, mainViewModel, selectExerciseViewModel)
        }
        composable(Screen.StartExerciseNav.route) {
            StartExerciseScreen(navController, mainViewModel)
        }
        composable(Screen.ExerciseNav.route) {
            ExerciseScreen(navController, mainViewModel)
        }
        composable(Screen.ExerciseSummaryNav.route) {
            ExerciseSummaryScreen(navController, mainViewModel, dataLayerViewModel)
        }
        composable(Screen.ExerciseNotAvailable.route){
            ExerciseNotAvailableScreen()
        }
    }
}