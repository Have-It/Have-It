package com.habit.ui.navigation

import androidx.annotation.DrawableRes
import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink
import com.habit.R
import com.habit.ui.navigation.NavigationRouteName.DEEP_LINK_SCHEME
import com.habit.ui.navigation.NavigationRouteName.HEALTH_CONNECT_SETTING
import com.habit.ui.navigation.NavigationRouteName.HOME
import com.habit.ui.navigation.NavigationRouteName.INITIAL_SETTING
import com.habit.ui.navigation.NavigationRouteName.LOGIN
import com.habit.ui.navigation.NavigationRouteName.SETTINGS
import com.habit.ui.navigation.NavigationTitle.HEALTH_CONNECT_SETTING_TITLE
import com.habit.ui.navigation.NavigationTitle.HOME_TITLE
import com.habit.ui.navigation.NavigationTitle.INITIAL_SETTING_TITLE
import com.habit.ui.navigation.NavigationTitle.LOGIN_TITLE
import com.habit.ui.navigation.NavigationTitle.SETTINGS_TITLE

// 네비게이션 요소 관리를 위한 인터페이스
interface Destination {
    val route: String
    val title: String?
    val deepLinks: List<NavDeepLink>
}

//바텀 네비게이션에 필요한 네비게이션 요소를 관리
sealed class BottomNav(
    override val route: String, // 바텀 네비 아이콘을 눌렀을때 이동할 라우트
    @DrawableRes val icon: Int, // 바텀 네비 아이콘
    override val title: String // 바텀 네비 아이콘 아래 텍스트
) : Destination {
    object Home : BottomNav(HOME, R.drawable.icon_home, HOME_TITLE)
    object Setting : BottomNav(SETTINGS, R.drawable.icon_settings, SETTINGS_TITLE)

    //이 바텀 네비게이션 바(MainNav sealed class)에 들어가는 네비게이션 요소들의 라우터 url을 리스트로 관리한다
    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "$DEEP_LINK_SCHEME$route" }
    )

    companion object {
        fun isMainRoute(route: String?): Boolean { //이 바텀 네비게이션이 존재하는 화면들의 리스트, 이 함수를 통해 바텀 네비가 있는지 여부 결정
            return when (route) {
                HOME, SETTINGS -> true

                else -> false
            }
        }
    }
}

/**************** Start Screen Navs ***************/

//로그인 화면으로 이동할때 사용하는 네비게이션 요소
object LoginNav : Destination {
    override val route: String = LOGIN
    override val title: String = LOGIN_TITLE
    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "$DEEP_LINK_SCHEME.$route" })
}

object InitialSettingNav : Destination {
    override val route: String = INITIAL_SETTING
    override val title: String = INITIAL_SETTING_TITLE
    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "$DEEP_LINK_SCHEME.$route" })
}

object HealthConnectSettingNav : Destination {
    override val route: String = HEALTH_CONNECT_SETTING
    override val title: String = HEALTH_CONNECT_SETTING_TITLE
    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "$DEEP_LINK_SCHEME.$route" })
}
