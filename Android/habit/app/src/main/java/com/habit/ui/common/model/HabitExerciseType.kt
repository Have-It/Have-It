package com.habit.ui.common.model

import androidx.annotation.DrawableRes
import com.habit.R

enum class HabitExerciseType (
    val cardText : String,
    @DrawableRes val icon: Int,
    val exerciseString: String
){
    WALK("걷기", R.drawable.icon_walk, "walk"),
    GOLF("골프", R.drawable.icon_golf, "golf"),
    BASKETBALL("농구", R.drawable.icon_basketball, "basketball"),
    RUN("달리기", R.drawable.icon_running, "run"),
    HIKING("등산", R.drawable.icon_hiking, "hiking"),
    TREADMILL("러닝머신", R.drawable.icon_treadmill, "treadmill"),
    MEDITATION("명상", R.drawable.icon_meditation, "meditation"),
    MARTIAL_ARTS("무술", R.drawable.icon_martial_arts, "martial_arts"),
    VOLLEYBALL("배구", R.drawable.icon_volleyball, "volleyball"),
    BADMINTON("배드민턴", R.drawable.icon_badminton, "badminton"),
    SWIM("수영", R.drawable.icon_swimming, "swim"),
    SQUAT("스쿼트", R.drawable.icon_squat, "squat"),
    ROCK_CLIMBING("암벽등반", R.drawable.icon_climbing, "rock_climbing"),
    BICYCLE("자전거", R.drawable.icon_biking, "bicycle"),
    SKIPPING_ROPE("줄넘기", R.drawable.icon_jump_rope, "skipping_rope"),
    SOCCER("축구", R.drawable.icon_soccer, "soccer"),
    DANCE("춤추기", R.drawable.icon_dancing, "dance"),
    TENNIS("테니스", R.drawable.icon_tennis, "tennis")
}