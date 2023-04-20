package com.example.QuizBattle.controller

import androidx.lifecycle.LifecycleCoroutineScope
import com.example.QuizBattle.controller.ScreenAndMediaControllers.ScreenNavigator
import com.example.QuizBattle.controller.gameStates.LoadDailyQuiz
import com.example.QuizBattle.controller.gameStates.PlayQuizState.PlayQuiz
import com.example.QuizBattle.controller.gameStates.LoadRandomQuiz
import com.example.QuizBattle.controller.gameStates.PresentQuizResults
import com.example.QuizBattle.model.QuizModel.GainedPoints
import com.example.QuizBattle.model.QuizModel.Quiz
import com.example.QuizBattle.model.QuizModel.QuizHolder
import com.example.QuizBattle.model.QuizModel.QuizTimer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

class GameEngine(
    private val context: GameController,
    private val lifecycleScope: LifecycleCoroutineScope,
    viewModelStoreOwner: ViewModelStoreOwner
){
    private lateinit var state: GameState
    private var quizHolder = QuizHolder("", Quiz("xx", "xx", "xx", "xx"), GainedPoints(0), QuizTimer())
    private val playerViewModel: PlayerViewModel = ViewModelProvider(viewModelStoreOwner)[PlayerViewModel::class.java]
    val fragmentLoadingState = FragmentLoadingState()
    private val screenNavigator = ScreenNavigator(context, fragmentLoadingState)
    init {
        playerViewModel.loadPlayerData()
        screenNavigator.init()
    }
    private fun newState(newState: GameState) {
        state = newState
        state.handleState(context)
    }

    fun onUserInput(event: UserInputEvent) {
        screenNavigator.navigateTo(event)
        lifecycleScope.launch {
            fragmentLoadingState.isLoading.first { !it }
            when (event) {
                UserInputEvent.LOAD_DAILY_QUIZ -> newState(LoadDailyQuiz(quizHolder))
                UserInputEvent.PLAY_DAILY_QUIZ -> newState(PlayQuiz(quizHolder))
                UserInputEvent.CHOOSE_CATEGORY -> newState(LoadRandomQuiz(quizHolder))
                UserInputEvent.PLAY_PLAYGROUND -> newState(PlayQuiz(quizHolder))
                UserInputEvent.RESULTS -> newState(PresentQuizResults(quizHolder, playerViewModel))
                UserInputEvent.RETURN_HOME -> return@launch
            }
        }
    }
}
