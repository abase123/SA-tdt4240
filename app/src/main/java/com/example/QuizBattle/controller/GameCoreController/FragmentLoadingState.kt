package com.example.QuizBattle.controller.GameCoreController

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


/**

The FragmentLoadingState class is responsible for managing the loading state of fragments within the quiz-based game.
It exposes an immutable StateFlow to allow other classes to observe the loading state while providing a method
to update the internal mutable state. This class is useful for coordinating UI transitions and ensuring smooth
navigation between game screens.
 */

class FragmentLoadingState {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

}
