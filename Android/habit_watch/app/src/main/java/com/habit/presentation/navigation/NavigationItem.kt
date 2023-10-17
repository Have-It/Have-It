package com.habit.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink
import com.habit.presentation.navigation.NavigationRouteName.DEEP_LINK_SCHEME
import com.habit.presentation.navigation.NavigationRouteName.EXERCISE
import com.habit.presentation.navigation.NavigationRouteName.EXERCISE_NOTAVAILABLE
import com.habit.presentation.navigation.NavigationRouteName.ExERCISE_SUMMARY
import com.habit.presentation.navigation.NavigationRouteName.SELECT_EXERCISE
import com.habit.presentation.navigation.NavigationRouteName.START_EXERCISE

// 네비게이션 요소 관리를 위한 인터페이스
interface Destination {
    val route: String
    val deepLinks: List<NavDeepLink>
}

sealed class Screen(
    override val route: String,
    override val deepLinks: List<NavDeepLink>
) :Destination{
    object SelectExerciseNav : Destination {
        override val route: String = SELECT_EXERCISE
        override val deepLinks: List<NavDeepLink> = listOf(
            navDeepLink { uriPattern = "$DEEP_LINK_SCHEME.$route" })
    }
    object StartExerciseNav: Destination{
        override val route: String = START_EXERCISE
        override val deepLinks: List<NavDeepLink> = listOf(
            navDeepLink { uriPattern = "$DEEP_LINK_SCHEME.$route" })
    }

    object ExerciseNav: Destination{
        override val route: String = EXERCISE
        override val deepLinks: List<NavDeepLink> = listOf(
            navDeepLink { uriPattern = "$DEEP_LINK_SCHEME.$route" })
    }

    object ExerciseSummaryNav: Destination{
        override val route: String = ExERCISE_SUMMARY
        override val deepLinks: List<NavDeepLink> = listOf(
            navDeepLink { uriPattern = "$DEEP_LINK_SCHEME.$route" })
    }

    object ExerciseNotAvailable: Destination{
        override val route: String = EXERCISE_NOTAVAILABLE
        override val deepLinks: List<NavDeepLink> = listOf(
            navDeepLink { uriPattern = "$DEEP_LINK_SCHEME.$route" })
    }
}
//
//fun NavController.navigateToTopLevel(screen: Destination, route: String = screen.route) {
//    navigate(route) {
//        popUpTo(graph.id) {
//            inclusive = true
//        }
//    }
//}