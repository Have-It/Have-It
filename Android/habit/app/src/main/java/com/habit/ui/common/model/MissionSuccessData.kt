package com.habit.ui.common.model

data class MissionSuccessData(
    var calorieMissionSuccessState: MissionStateType = MissionStateType.NOT_ACHIEVED,
    var sleepMissionSuccessState: MissionStateType = MissionStateType.NOT_ACHIEVED,
    var freeMissionSuccessState: MissionStateType = MissionStateType.NOT_ACHIEVED
)