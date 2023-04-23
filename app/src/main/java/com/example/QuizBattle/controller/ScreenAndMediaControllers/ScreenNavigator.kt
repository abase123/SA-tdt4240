package com.example.QuizBattle.controller.ScreenAndMediaControllers

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameActivity
import com.example.QuizBattle.controller.GameCoreController.UserInputEvent
import com.example.QuizBattle.controller.GameCoreController.FragmentLoading
import com.google.android.material.bottomnavigation.BottomNavigationView
/**

ScreenNavigator is a class responsible for managing navigation between screens in the Quiz Battle app.
It works closely with the GameActivity and FragmentLoading to navigate between different screens
based on user input events, manage bottom navigation visibility, and control background music during navigation.
@param gameActivity The main activity of the app.
@param fragmentLoading A helper class to handle loading states of fragments.
 */


class ScreenNavigator(private val gameActivity: GameActivity, private  val fragmentLoading: FragmentLoading) {
    private val mediaController: MediaController = MediaController(gameActivity)
   private lateinit var bottomNavigationMenu:BottomNavigationView

    fun init(){
        setupBottomNavigation()
        mediaController.playBackGroundTrack()

    }
    private fun getNavController(): NavController {
        val navHostFragment = gameActivity.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.navController
    }

    fun navigateTo(event: UserInputEvent) {
        val navController = getNavController()
        setScreenMusic(event)
        fragmentLoading.setLoading(true)
        when (event) {
            UserInputEvent.LOAD_DAILY_QUIZ -> navController.navigate(R.id.loadingQuiz)
            UserInputEvent.LOAD_PLAYGROUND_QUIZ -> navController.navigate(R.id.loadingPlaygroundQuiz)
            UserInputEvent.PLAY_DAILY_QUIZ -> navController.navigate(R.id.quiz)
            UserInputEvent.SELECT_THEME -> navController.navigate(R.id.themes)
            UserInputEvent.PLAY_PLAYGROUND -> navController.navigate(R.id.quiz)
            UserInputEvent.RETURN_HOME -> navController.navigate(R.id.home)
            UserInputEvent.RESULTS -> navController.navigate(R.id.results)
        }
        hideBottomNavigation(event)
    }

    private fun setScreenMusic(event: UserInputEvent){
        mediaController.pause()
        when (event) {
            UserInputEvent.PLAY_DAILY_QUIZ -> mediaController.playQuizTrack()
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
        val bottomNavigationView: BottomNavigationView = gameActivity.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(getNavController())
        bottomNavigationMenu=bottomNavigationView
    }

}