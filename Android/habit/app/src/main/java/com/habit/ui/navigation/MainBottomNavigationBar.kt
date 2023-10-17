package com.habit.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.habit.ui.theme.HabitBlueGray
import com.habit.ui.theme.HabitPurpleNormal
import com.habit.ui.theme.HabitWhite


private const val TAG = "MainBottomNavigationBar"

@Composable
fun MainBottomNavigationBar(navController: NavHostController) {
    val bottomNavigationItems = listOf(
        BottomNav.Home,
        BottomNav.Setting
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val routes = listOf<String>(BottomNav.Home.route, BottomNav.Setting.route)

    if (currentRoute in routes) {
        NavigationBar(
            containerColor = HabitPurpleNormal,
            tonalElevation = 0.dp,
            modifier =
            Modifier
                .graphicsLayer {
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp
                    )
                    clip = true
                    shadowElevation = 20f
                }
        ) {
            bottomNavigationItems.forEach { item ->
                Spacer(modifier = Modifier.size(4.dp))
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = HabitWhite,
                        selectedTextColor = HabitWhite,
                        unselectedIconColor = HabitBlueGray,
                        unselectedTextColor = Color.Transparent,
                        indicatorColor = HabitPurpleNormal
                    ),
                    label = {
                        Text(text = item.title)
                    },
                    icon = { Icon(painter = painterResource(id = item.icon), item.title) },
                    selected = currentRoute == item.route, //언제 selected 상태가 되는지
                    onClick = { //네비게이션 아이템 클릭시
                        navController.navigate(item.route) {
                            Log.d(TAG, "MainBottomNavigationBar: current ${currentRoute.toString()}")
                           // if (currentRoute.toString() != item.route) { // 가장 최근 화면이 이동 목적지와 다른 경우에만
                                Log.d(TAG, "MainBottomNavigationBar: remove!!")
                                popUpTo(currentRoute.toString()) {
                                    inclusive = true
                                } // 스택에 있는 가장 최근 화면 제거
                          //  }
//                            else { // 가장 최근 화면이 이동 목적지와 동일한경우 새로 쌓지 않고 재사용
//                                launchSingleTop = true
//                            }
                        }
                    })
            }
        }
    }

}

@Composable
@Preview
fun PrevMainBottomNavigationBar() {
    val homeNavController = rememberNavController()
    MainBottomNavigationBar(navController = homeNavController)
}