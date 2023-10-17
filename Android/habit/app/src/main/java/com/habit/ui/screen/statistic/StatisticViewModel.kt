package com.habit.ui.screen.statistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habit.domain.model.statistics.DateMissionCnt
import com.habit.domain.useCase.example.ExampleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val useCases: ExampleUseCases,
) : ViewModel() {


    private val _dateAndMissionCnt: MutableStateFlow<List<DateMissionCnt>> = MutableStateFlow(listOf())
    val dateAndMissionCnt: StateFlow<List<DateMissionCnt>> = _dateAndMissionCnt
    fun getDateSuccessInfo()  {
        viewModelScope.launch {
            // useCase를 부름
            _dateAndMissionCnt.emit(listOf(
                DateMissionCnt(2023,9,1,3),
                DateMissionCnt(2023,9,2,1),
                DateMissionCnt(2023,9,3,2),
                DateMissionCnt(2023,9,4,0),
                DateMissionCnt(2023,9,5,1),
                DateMissionCnt(2023,9,6,2),
                DateMissionCnt(2023,9,7,3),
                DateMissionCnt(2023,9,8,1),
                DateMissionCnt(2023,8,24,1),
                DateMissionCnt(2023,8,25,2),
                DateMissionCnt(2023,8,26,3),
                DateMissionCnt(2023,8,27,2),
            ))
        }

    }


}