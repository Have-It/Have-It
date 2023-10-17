package com.habit.domain.useCase.example

import com.habit.domain.model.example.Post
import com.habit.domain.onSuccess
import com.habit.domain.repository.MainRepository


class GetFirstPostUseCase(private val mainRepository: MainRepository) {
    suspend operator fun invoke(): Post {
        mainRepository.getFirstPost().onSuccess {
            return it
        }
        return Post()
    }
}