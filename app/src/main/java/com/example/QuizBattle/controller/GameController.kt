package com.example.QuizBattle.controller

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.gameStates.*
import com.example.QuizBattle.model.QuizModel.Quiz
import com.example.QuizBattle.model.QuizModel.QuizHolder
import com.example.QuizBattle.model.QuizModel.GainedPoints
import com.google.android.material.bottomnavigation.BottomNavigationView


class GameController : AppCompatActivity(), UserInputListener {
    private lateinit var state: GameState
    private var quizHolder = QuizHolder(Quiz("xx", "xx", "xx", "xx"), GainedPoints(0))

    private fun newState(newState: GameState) {
        state = newState
        Log.d("STATE", "Quiz accessed: $state")
        state.handle(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onUserInput(event: UserInputEvent) {
        navigateTo(event)
        when (event) {
            UserInputEvent.LOAD_DAILY_QUIZ -> newState(LoadQuiz(this.quizHolder))
            UserInputEvent.PLAY_DAILYQUIZ -> newState(PlayDailyQuiz(this.quizHolder))
            UserInputEvent.PLAY_FRIEND -> newState(PlayFriendsQuiz(this.quizHolder))
            UserInputEvent.RESULTS -> newState(PresentQuizResults(quizHolder))
            UserInputEvent.RETURN_HOME -> return
        }
    }

    private fun navigateTo(event: UserInputEvent) {
        val navController = getNavController()
        when (event) {
            UserInputEvent.LOAD_DAILY_QUIZ -> navController.navigate(R.id.loadingQuiz)
            UserInputEvent.PLAY_DAILYQUIZ -> navController.navigate(R.id.quiz)
            UserInputEvent.PLAY_FRIEND -> navController.navigate(R.id.quiz)
            UserInputEvent.RETURN_HOME -> navController.navigate(R.id.home)
            UserInputEvent.RESULTS -> navController.navigate(R.id.results)

        }
    }
     private fun getNavController(): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.navController
    }
}