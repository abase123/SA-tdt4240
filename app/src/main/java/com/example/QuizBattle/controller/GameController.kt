package com.example.QuizBattle.controller

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.gameStates.*
import com.example.QuizBattle.model.QuizModel.Quiz
import com.example.QuizBattle.model.QuizModel.QuizHolder
import com.example.QuizBattle.model.QuizModel.GainedPoints
import com.example.QuizBattle.model.QuizModel.QuizTimer


class GameController : AppCompatActivity(), UserInputListener {

    private lateinit var state: GameState
    private var quizHolder = QuizHolder(Quiz("xx", "xx", "xx", "xx"), GainedPoints(0), QuizTimer())
    private  val screenNavigator:ScreenNavigator= ScreenNavigator(this)

    private fun newState(newState: GameState) {
        state = newState
        Log.d("STATE", "Quiz accessed: $state")
        state.handle(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        screenNavigator.init()
    }

    override fun onUserInput(event: UserInputEvent) {
        screenNavigator.navigateTo(event)
        when (event) {
            UserInputEvent.LOAD_DAILY_QUIZ -> newState(LoadDailyQuiz(this.quizHolder))
            UserInputEvent.PLAY_DAILYQUIZ -> newState(PlayDailyQuiz(this.quizHolder))
            UserInputEvent.PLAY_FRIEND -> newState(PlayFriendsQuiz(this.quizHolder))
            UserInputEvent.RESULTS -> newState(PresentQuizResults(this.quizHolder))
            UserInputEvent.RETURN_HOME -> return
        }
    }

}