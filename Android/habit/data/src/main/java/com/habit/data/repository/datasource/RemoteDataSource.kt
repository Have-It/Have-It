package com.habit.data.repository.datasource

import com.habit.data.entity.response.Post
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getFirstPost(): Response<Post>
    suspend fun getAllPosts(): Response<List<Post>>
}