package com.example.QuizBattle.controller

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.QuizBattle.model.FirestoreRepoes.FireStoreRepoUser
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.ScreenAndMediaControllers.ScreenNavigator
import com.example.QuizBattle.controller.gameStates.*
import com.example.QuizBattle.controller.gameStates.PlayDailyQuizState.PlayDailyQuiz
import com.example.QuizBattle.model.PlayerModel.Player
import com.example.QuizBattle.model.QuizModel.Quiz
import com.example.QuizBattle.model.QuizModel.QuizHolder
import com.example.QuizBattle.model.QuizModel.GainedPoints
import com.example.QuizBattle.model.QuizModel.QuizTimer
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class GameController : AppCompatActivity(), ViewChangeListener {
    private lateinit var state: GameState
    private var quizHolder = QuizHolder(Quiz("xx", "xx", "xx", "xx"), GainedPoints(0), QuizTimer())
    private lateinit var playerViewModel: PlayerViewModel
    val fragmentLoadingState = FragmentLoadingState()
    private val screenNavigator: ScreenNavigator = ScreenNavigator(this,fragmentLoadingState)
    private fun newState(newState: GameState) {
        state = newState
        state.handle(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        playerViewModel= ViewModelProvider(this)[PlayerViewModel::class.java]
        playerViewModel.loadPlayerData()
        screenNavigator.init()
    }

    override fun onUserInput(event: UserInputEvent) {
        screenNavigator.navigateTo(event)
        lifecycleScope.launch {
            fragmentLoadingState.isLoading.first {!it}
            when (event) {
                UserInputEvent.LOAD_DAILY_QUIZ -> newState(LoadDailyQuiz(this@GameController.quizHolder))
                UserInputEvent.PLAY_DAILYQUIZ -> newState(PlayDailyQuiz(this@GameController.quizHolder))
                UserInputEvent.PLAY_FRIEND -> newState(PlayFriendsQuiz(this@GameController.quizHolder))
                UserInputEvent.RESULTS -> newState(PresentQuizResults(this@GameController.quizHolder,playerViewModel))
                UserInputEvent.RETURN_HOME -> return@launch
            }
        }
    }
}