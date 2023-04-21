package com.example.QuizBattle.controller

import androidx.lifecycle.LifecycleCoroutineScope
import com.example.QuizBattle.controller.ScreenAndMediaControllers.ScreenNavigator
import com.example.QuizBattle.controller.gameStates.LoadDailyQuiz
import com.example.QuizBattle.controller.gameStates.PlayQuizState.PlayQuiz
import com.example.QuizBattle.controller.gameStates.LoadPlayGroundQuiz
import com.example.QuizBattle.controller.gameStates.PresentQuizResults
import com.example.QuizBattle.model.QuizModel.GainedPoints
import com.example.QuizBattle.model.QuizModel.Quiz
import com.example.QuizBattle.model.QuizModel.QuizHolder
import com.example.QuizBattle.model.QuizModel.QuizTimer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.QuizBattle.controller.gameStates.SelectTheme.SelectTheme


/**

The GameEngine class is responsible for managing the game flow, state transitions, and user interactions
within a quiz-based game. It utilizes a ScreenNavigator to handle fragment navigation and interacts with
the PlayerViewModel to manage player data. This class also coordinates the game states, which are represented
by GameState objects, and handles UserInputEvents to drive game progression.
 */

class GameEngine(
    private val context: GameController,
    private val lifecycleScope: LifecycleCoroutineScope,
    viewModelStoreOwner: ViewModelStoreOwner
){
    private lateinit var state: GameState
    private var quizHolder = QuizHolder("", Quiz("xx", "xx", "xx", "xx"),
        GainedPoints(0,0), QuizTimer())
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
                UserInputEvent.SELECT_THEME -> newState(SelectTheme(quizHolder))
                UserInputEvent.LOAD_PLAYGROUND_QUIZ -> newState(LoadPlayGroundQuiz(quizHolder))
                UserInputEvent.PLAY_PLAYGROUND -> newState(PlayQuiz(quizHolder))
                UserInputEvent.RESULTS -> newState(PresentQuizResults(quizHolder, playerViewModel))
                UserInputEvent.RETURN_HOME -> return@launch
            }
        }
    }
}
