package com.habit.ui.navigation

//네비게이션에 필요한 딥링크 주소에 들어가는 값을 관리
object NavigationRouteName{
    const val DEEP_LINK_SCHEME = "habit://"

    //main
    const val HOME = "home_screen"
    const val STATISTIC = "statistic_screen"
    const val SETTINGS = "settings_screen"

    //start
    const val LOGIN = "login_screen"
    const val INITIAL_SETTING = "initial_setting"
    const val HEALTH_CONNECT_SETTING = "health_connect_setting"
}

//각 화면에 필요한 제목들
object NavigationTitle{
    const val HOME_TITLE = "홈"
    const val STATISTIC_TITLE = "통계"
    const val SETTINGS_TITLE = "설정"

    const val LOGIN_TITLE = "로그인"
    const val INITIAL_SETTING_TITLE = "초기설정"
    const val HEALTH_CONNECT_SETTING_TITLE = "헬스커넥트 설정"

}