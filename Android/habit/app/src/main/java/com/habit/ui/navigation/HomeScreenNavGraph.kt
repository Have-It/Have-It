package com.habit.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.habit.ui.screen.HealthConnectSetting.HealthConnectSettingScreen
import com.habit.ui.screen.home.HomeScreen
import com.habit.ui.screen.initialsetting.InitialSettingScreen
import com.habit.ui.screen.login.LoginScreen
import com.habit.ui.screen.setting.SettingScreen

@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreenNavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    AnimatedNavHost(
        modifier = Modifier.padding(innerPadding),
        navController = navController,
        startDestination = LoginNav.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {

        composable(
            route = LoginNav.route,
            deepLinks = LoginNav.deepLinks
        ) {
            LoginScreen(navController = navController)
        }

        composable(
            route = InitialSettingNav.route,
            deepLinks = InitialSettingNav.deepLinks
        ) {
            InitialSettingScreen(navController = navController)
        }

        composable(
            route = HealthConnectSettingNav.route,
            deepLinks = HealthConnectSettingNav.deepLinks
        ){
            HealthConnectSettingScreen(navController = navController)
        }

        composable(
            route = BottomNav.Home.route,
            deepLinks = BottomNav.Home.deepLinks
        ) {
            HomeScreen(navController = navController)
        }

        composable(
            route = BottomNav.Setting.route,
            deepLinks = BottomNav.Setting.deepLinks
        ) {
            SettingScreen(navController = navController)
        }

//        composable(
//            route = GoLoginNav.route,
//            deepLinks = GoLoginNav.deepLinks
//        ){
//            StartScreen()
//        }
    }
}