package com.habit.domain.useCase.home

import android.util.Log
import com.habit.domain.NetworkResult
import com.habit.domain.model.home.HomeDataDto
import com.habit.domain.onSuccess
import com.habit.domain.repository.UserRepository

private const val TAG = "GetHomeDataUseCase"
class GetHomeDataUseCase (private val userRepository: UserRepository)  {
    suspend operator fun invoke(): NetworkResult<HomeDataDto>{
        return userRepository.getHomeData()

//        res.onSuccess {
//            return it
//        }
//        return HomeDataDto(-1, -1, false, false, false)
//
    }
}