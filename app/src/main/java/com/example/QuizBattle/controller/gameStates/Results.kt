package com.example.QuizBattle.controller.gameStates

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.model.QuizModel.DailyQuizHolder

class Results(override var quizHolder: DailyQuizHolder) :GameState {
    override fun handle(context: GameController) {
        val currentFragment=getCurrentFragment(context)
    }


    private fun getCurrentFragment(context: GameController): Fragment {
        val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments[0]
    }

}