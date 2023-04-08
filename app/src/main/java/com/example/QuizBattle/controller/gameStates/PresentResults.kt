package com.example.QuizBattle.controller.gameStates

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.controller.QuizViewModel
import com.example.QuizBattle.model.QuizModel.QuizHolder

class PresentResults(override var quizHolder: QuizHolder) :GameState {
    private lateinit var quizViewModel: QuizViewModel
    override fun handle(context: GameController) {
        val currentFragment=getCurrentFragment(context)
        setViewModel(context)
    }


    private fun setViewModel(context: GameController) {
        val currentFragment = getCurrentFragment(context)
        quizViewModel = ViewModelProvider(currentFragment.requireActivity())[QuizViewModel::class.java]

    }
    private fun showResults(){

    }

    private fun ratingCalc(){

    }

    private fun sendToDatabase(){

    }

    private fun getCurrentFragment(context: GameController): Fragment {
        val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments[0]
    }


}