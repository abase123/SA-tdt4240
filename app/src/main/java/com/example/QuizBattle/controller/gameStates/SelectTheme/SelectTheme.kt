package com.example.QuizBattle.controller.gameStates.SelectTheme

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.controller.GameState
import com.example.QuizBattle.controller.gameStates.PlayQuizState.PlayQuizViewModel
import com.example.QuizBattle.model.QuizModel.QuizHolder

class SelectTheme(override var quizHolder: QuizHolder) :GameState {
    private lateinit var themeViewModel: SelectThemeViewModel

    override fun handleState(context: GameController) {
        setViewModel(context)

    }

    private fun setViewModel(context: GameController) {
        val currentFragment = getCurrentFragment(context)
        themeViewModel = ViewModelProvider(currentFragment.requireActivity())[SelectThemeViewModel::class.java]
    }


    private fun getCurrentFragment(context: GameController): Fragment {
        val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments[0]
    }

}