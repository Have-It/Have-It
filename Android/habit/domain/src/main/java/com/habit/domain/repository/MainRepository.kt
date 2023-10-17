package com.habit.domain.repository

import com.habit.domain.NetworkResult
import com.habit.domain.model.example.Post

interface MainRepository {

    suspend fun getFirstPost(): NetworkResult<Post>
    suspend fun getAllPost() : NetworkResult<List<Post>>

}