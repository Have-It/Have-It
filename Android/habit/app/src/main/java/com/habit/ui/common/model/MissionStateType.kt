package com.habit.ui.common.model

import androidx.annotation.DrawableRes
import com.habit.R

enum class MissionStateType(val description: String, @DrawableRes val icon: Int) {
    NOT_ACHIEVED("코인 수령 불가", R.drawable.icon_flat_not_done),
    REWARD_POSSIBLE("코인 수령 가능", R.drawable.icon_flat_coin),
    ALREADY_REWARDED("코인 이미 수령함", R.drawable.icon_flat_achived)
}