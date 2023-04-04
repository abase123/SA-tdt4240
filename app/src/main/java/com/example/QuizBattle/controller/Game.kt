package com.example.QuizBattle.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.statePattern.*
import com.example.QuizBattle.view.LoadingQuiz
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.auth.User

class Game : AppCompatActivity(), UserInputListener {
    private var state: GameState? = null

    private fun newState(newState: GameState) {
        state = newState
        state?.handle(this)
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
            UserInputEvent.LOAD_DAILY_QUIZ -> newState(LoadQuiz())
            UserInputEvent.PLAY_DAILYQUIZ -> newState(DailyQuiz())
            UserInputEvent.PLAY_FRIEND -> newState(FriendsModeQuiz())
            else -> navigateTo(UserInputEvent.RETURN_HOME)
        }
    }

    private fun navigateTo(event: UserInputEvent) {
        val navController = getNavController()
        when (event) {
            UserInputEvent.LOAD_DAILY_QUIZ -> navController.navigate(R.id.loadingQuiz)
            UserInputEvent.PLAY_DAILYQUIZ -> navController.navigate(R.id.EndQuiz)
            UserInputEvent.PLAY_FRIEND -> navController.navigate(R.id.StartQuiz)
            UserInputEvent.RETURN_HOME -> navController.navigate(R.id.home)
        }
    }

    private fun getNavController(): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.navController
    }
}