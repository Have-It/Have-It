package com.habit.data.model

import androidx.annotation.DrawableRes
import androidx.health.services.client.data.ExerciseType
import com.habit.R

enum class HabitExerciseType(
    val excerciseName: String,
    @DrawableRes val icon: Int,
    val exerciseType: ExerciseType,
    val exerciseTypeString: String
) {
    WALK("걷기", R.drawable.icon_background_walk, ExerciseType.WALKING,"walk"),
    GOLF("골프", R.drawable.icon_background_golf, ExerciseType.GOLF, "golf"),
    BASKETBALL("농구", R.drawable.icon_background_basketball, ExerciseType.BASKETBALL,"basketball"),
    RUN("달리기", R.drawable.icon_background_running, ExerciseType.RUNNING,"run"),
    HIKING("등산", R.drawable.icon_background_hiking, ExerciseType.HIKING, "hiking"),
    TREADMILL("러닝머신", R.drawable.icon_background_treadmill, ExerciseType.RUNNING_TREADMILL, "treadmill"),
    MEDITATION("명상", R.drawable.icon_background_meditation, ExerciseType.MEDITATION, "meditation"),
    MARTIAL_ARTS("무술", R.drawable.icon_background_martial_arts, ExerciseType.MARTIAL_ARTS, "martial_arts"),
    VOLLEYBALL("배구", R.drawable.icon_background_volleyball, ExerciseType.VOLLEYBALL, "volleyball"),
    BADMINTON("배드민턴", R.drawable.icon_background_badminton, ExerciseType.BADMINTON, "badminton"),
    SWIM("수영", R.drawable.icon_background_swimming, ExerciseType.SWIMMING_OPEN_WATER, "swim"),
    SQUAT("스쿼트", R.drawable.icon_background_squat, ExerciseType.SQUAT, "squat"),
    ROCK_CLIMBING("암벽등반", R.drawable.icon_background_climbing, ExerciseType.ROCK_CLIMBING, "rock_climbing"),
    BICYCLE("자전거", R.drawable.icon_background_biking, ExerciseType.BIKING, "bicycle"),
    SKIPPING_ROPE("줄넘기", R.drawable.icon_background_jump_rope, ExerciseType.JUMP_ROPE, "skipping_rope"),
    SOCCER("축구", R.drawable.icon_background_soccer, ExerciseType.SOCCER, "soccer"),
    DANCE("춤추기", R.drawable.icon_background_dancing, ExerciseType.DANCING, "dance"),
    TENNIS("테니스", R.drawable.icon_background_tennis, ExerciseType.TENNIS, "tennis")
}