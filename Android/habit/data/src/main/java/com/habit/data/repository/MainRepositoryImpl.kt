package com.habit.data.repository

import com.habit.data.api.handleApi
import com.habit.data.mapper.toDomain
import com.habit.data.repository.datasource.RemoteDataSource
import com.habit.domain.NetworkResult
import com.habit.domain.model.example.Post
import com.habit.domain.repository.MainRepository

class MainRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
//    private val localDataSource: LocalDataSource,
) : MainRepository {
    override suspend fun getFirstPost(): NetworkResult<Post> {
        return handleApi { remoteDataSource.getFirstPost().body()!!.toDomain() }
    }

    override suspend fun getAllPost(): NetworkResult<List<Post>> {
        return handleApi { remoteDataSource.getAllPosts().body()!!.map {
            it.toDomain()
        } }
    }


}

//    {
//    override fun getPopularMovies() =
//        movieRemoteDataSource.getPopularMovies()
//
//    override fun getMoviesFromDB(movieId: Int): Flow<Movie> =
//        movieLocalDataSource.getMoviesFromDB(movieId)
//


