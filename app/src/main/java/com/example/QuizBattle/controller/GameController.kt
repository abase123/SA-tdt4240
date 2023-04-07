package com.example.QuizBattle.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.gameStates.*
import com.example.QuizBattle.model.QuizModel.Quiz
import com.google.android.material.bottomnavigation.BottomNavigationView

class GameController : AppCompatActivity(), UserInputListener {
    private lateinit var state: GameState
    private var quizHolder = QuizHolder(Quiz("xx", "xx", "xx", "xx"))

    private fun newState(newState: GameState) {
        state = newState
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
            UserInputEvent.PLAY_DAILYQUIZ -> newState(DailyQuiz(this.quizHolder))
            UserInputEvent.PLAY_FRIEND -> newState(FriendsModeQuiz(this.quizHolder))
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
        }
    }
     private fun getNavController(): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.navController
    }
}