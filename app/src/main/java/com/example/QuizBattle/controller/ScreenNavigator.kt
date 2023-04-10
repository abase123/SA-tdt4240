package com.example.QuizBattle.controller

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.QuizBattle.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class ScreenNavigator(private val gameController: AppCompatActivity) {
    private fun getNavController(): NavController {
        val navHostFragment = gameController.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.navController
    }
    fun navigateTo(event: UserInputEvent) {
        val navController = getNavController()
        when (event) {
            UserInputEvent.LOAD_DAILY_QUIZ -> navController.navigate(R.id.loadingQuiz)
            UserInputEvent.PLAY_DAILYQUIZ -> navController.navigate(R.id.quiz)
            UserInputEvent.PLAY_FRIEND -> navController.navigate(R.id.quiz)
            UserInputEvent.RETURN_HOME -> navController.navigate(R.id.home)
            UserInputEvent.RESULTS -> navController.navigate(R.id.results)
        }
    }


    fun setupBottomNavigation() {
        val bottomNavigationView = gameController.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment = gameController.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
    }

}