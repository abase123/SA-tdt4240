package com.example.QuizBattle.controller.GameCoreController

import androidx.lifecycle.LifecycleCoroutineScope
import com.example.QuizBattle.controller.ScreenAndMediaControllers.ScreenNavigator
import com.example.QuizBattle.controller.gameStates.LoadDailyQuiz
import com.example.QuizBattle.controller.gameStates.PlayQuizState.PlayQuiz
import com.example.QuizBattle.controller.gameStates.LoadPlayGroundQuiz
import com.example.QuizBattle.controller.gameStates.HandleQuizResults
import com.example.QuizBattle.model.QuizModel.GainedPoints
import com.example.QuizBattle.model.QuizModel.MainQuizComponents.Quiz
import com.example.QuizBattle.model.QuizModel.QuizHolder
import com.example.QuizBattle.model.QuizModel.QuizTimer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.QuizBattle.controller.*
import com.example.QuizBattle.controller.gameStates.SelectTheme.SelectTheme
/**
 * The GameEngine class is responsible for managing the game's state and user interactions.
 * It takes care of processing user input events, updating the game state, and navigating
 * between different screens of the application. This class implement the core flow in the game.
 *
 * @param context The GameActivity instance that hosts the game.
 * @param lifecycleScope The LifecycleCoroutineScope used to launch coroutines tied to the activity's lifecycle.
 * @param playerViewModel The PlayerViewModel used to manage player data.
 * @param screenNavigator The ScreenNavigator responsible for handling navigation between different screens.
 * @param fragmentLoading The FragmentLoading used to display loading states during navigation and state changes.
 * @param quizHolder An optional QuizHolder parameter containing quiz data, with a default empty QuizHolder.
 */

class GameEngine(
    private val context: GameActivity,
    private val lifecycleScope: LifecycleCoroutineScope,
    private val playerViewModel: PlayerViewModel,
    private val screenNavigator: ScreenNavigator,
    val fragmentLoading: FragmentLoading,
    private val quizHolder: QuizHolder = QuizHolder("", Quiz("xx", "xx", "xx", "xx"),
        GainedPoints(0,0), QuizTimer())
) {

    private lateinit var state:GameState

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
            fragmentLoading.isLoading.first { !it }
            when (event) {
                UserInputEvent.LOAD_DAILY_QUIZ -> newState(LoadDailyQuiz(quizHolder))
                UserInputEvent.PLAY_DAILY_QUIZ -> newState(PlayQuiz(quizHolder))
                UserInputEvent.SELECT_THEME -> newState(SelectTheme(quizHolder))
                UserInputEvent.LOAD_PLAYGROUND_QUIZ -> newState(LoadPlayGroundQuiz(quizHolder))
                UserInputEvent.PLAY_PLAYGROUND -> newState(PlayQuiz(quizHolder))
                UserInputEvent.RESULTS -> newState(HandleQuizResults(quizHolder, playerViewModel))
                UserInputEvent.RETURN_HOME -> return@launch
            }
        }
    }
}