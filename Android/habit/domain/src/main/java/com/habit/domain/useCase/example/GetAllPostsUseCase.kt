package com.habit.domain.useCase.example

import com.habit.domain.model.example.Post
import com.habit.domain.onSuccess
import com.habit.domain.repository.MainRepository


class GetAllPostsUseCase(private val mainRepository: MainRepository) {
    suspend operator fun invoke(): List<Post> {
        mainRepository.getAllPost().onSuccess {
            return it
        }
        return listOf()
    }
}