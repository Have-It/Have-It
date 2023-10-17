package com.habit.ui.screen.example

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habit.domain.model.example.Post
import com.habit.domain.useCase.example.ExampleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ExampleViewModel_싸피"

@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val useCases: ExampleUseCases,
) : ViewModel() {

    private val _firstPost: MutableStateFlow<Post> = MutableStateFlow(Post())
    val firstPost: StateFlow<Post?> = _firstPost

    private val _postList: MutableStateFlow<List<Post>> = MutableStateFlow(listOf())
    val postList: StateFlow<List<Post?>> = _postList

    fun getFirstPost() {
        Log.d(TAG, "getFirstPost: ddd")
        viewModelScope.launch {
            _firstPost.emit(useCases.getFirstPostUseCase.invoke())
        }
    }

    fun getAllPost() {
        viewModelScope.launch {
            _postList.emit(useCases.getAllPostsUseCase.invoke())
        }
    }

}