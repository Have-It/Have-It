package com.habit.data.repository.datasourceImpl


import com.habit.data.api.MainService
import com.habit.data.entity.response.Post
import com.habit.data.repository.datasource.RemoteDataSource
import retrofit2.Response


class RemoteDataSourceImpl(private val apiService: MainService) :
    RemoteDataSource {

    override suspend fun getFirstPost() : Response<Post> {
        return apiService.getFirstPost()
    }

    override suspend fun getAllPosts(): Response<List<Post>> {
        return apiService.getAllPosts()
    }


//        return Pager(
//            config = PagingConfig(pageSize = 20),
//            remoteMediator = MovieRemoteMediator(
//                movieApi,
//                movieDB
//            ),
//            pagingSourceFactory = pagingSourceFactory,
//        ).flow

}