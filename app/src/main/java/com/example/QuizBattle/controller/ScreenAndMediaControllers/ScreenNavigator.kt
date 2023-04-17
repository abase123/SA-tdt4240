package com.example.QuizBattle.controller.ScreenAndMediaControllers

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.controller.UserInputEvent
import com.example.QuizBattle.controller.FragmentLoadingState
import com.google.android.material.bottomnavigation.BottomNavigationView

class ScreenNavigator(private val gameController: GameController,private  val fragmentLoadingState: FragmentLoadingState) {
    private  var mediaController: MediaController = MediaController(gameController)
   private lateinit var bottomNavigationMenu:BottomNavigationView

    fun init(){
        setupBottomNavigation()
        mediaController.playBackGroundTrack()

    }
    private fun getNavController(): NavController {
        val navHostFragment = gameController.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.navController
    }

    fun navigateTo(event: UserInputEvent) {
        val navController = getNavController()
        setScreenMusic(event)
        fragmentLoadingState.setLoading(true)
        when (event) {
            UserInputEvent.LOAD_DAILY_QUIZ -> navController.navigate(R.id.loadingQuiz)
            UserInputEvent.PLAY_DAILYQUIZ -> navController.navigate(R.id.quiz)
            UserInputEvent.PLAY_FRIEND -> navController.navigate(R.id.quiz)
            UserInputEvent.RETURN_HOME -> navController.navigate(R.id.home)
            UserInputEvent.RESULTS -> navController.navigate(R.id.results)
        }
        hideBottomNavigation(event)
    }

    private fun setScreenMusic(event: UserInputEvent){
        mediaController.pause()
        when (event) {
            UserInputEvent.PLAY_DAILYQUIZ -> mediaController.playQuizTrack()
            UserInputEvent.LOAD_DAILY_QUIZ -> mediaController.playQuizTrack()
            UserInputEvent.RESULTS -> mediaController.playResultsTrack()
            else -> {mediaController.playBackGroundTrack()}
        }
    }

    private fun hideBottomNavigation(event: UserInputEvent){
        if (event!= UserInputEvent.RETURN_HOME){
            bottomNavigationMenu.visibility=View.GONE
        }

        else{
            bottomNavigationMenu.visibility=View.VISIBLE
        }
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView: BottomNavigationView = gameController.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment = gameController.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationMenu=bottomNavigationView
    }

}