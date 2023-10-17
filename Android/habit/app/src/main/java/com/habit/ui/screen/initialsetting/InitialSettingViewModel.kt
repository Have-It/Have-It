package com.habit.ui.screen.initialsetting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habit.domain.model.mission.MissionDto
import com.habit.domain.useCase.mission.MissionUseCases
import com.habit.ui.common.model.FreeMissionType
import com.habit.ui.screen.login.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitialSettingViewModel @Inject constructor(
    private val useCases: MissionUseCases
) : ViewModel() {

    fun putMission(userId: Int, calorie: Int, hour: Int, minute: Int, freeMissionType: FreeMissionType, detail: String) {
        viewModelScope.launch {
            useCases.putMissionUseCase.invoke(
                missionDto = MissionDto(
                    userId,
                    calorie,
                    hour,
                    minute,
                    freeMissionType.type,
                    detail
                )
            )
        }
    }

}