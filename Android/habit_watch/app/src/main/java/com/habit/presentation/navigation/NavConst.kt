package com.habit.presentation.navigation

//네비게이션에 필요한 딥링크 주소에 들어가는 값을 관리
object NavigationRouteName{
    const val DEEP_LINK_SCHEME = "habit://"

    //main
    const val SELECT_EXERCISE = "select_exercise"
    const val START_EXERCISE = "start_exercise"
    const val EXERCISE = "exercise"
    const val ExERCISE_SUMMARY = "exercise_summary"
    const val EXERCISE_NOTAVAILABLE = "exercise_notavailable"
}