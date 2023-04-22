package com.example.QuizBattle.controller.gameStates.SelectTheme

import OnThemeChangeListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameActivity
import com.example.QuizBattle.controller.GameState
import com.example.QuizBattle.view.ChooseThemeView
import com.example.QuizBattle.model.QuizModel.QuizHolder

class SelectTheme(override var quizHolder: QuizHolder) :GameState, OnThemeChangeListener{
    override fun handleState(context: GameActivity) {
        val currentFragment = getCurrentFragment(context) as ChooseThemeView
        currentFragment.themeChangeListener = this
    }

    private fun getCurrentFragment(context: GameActivity): Fragment {
        val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments[0]
    }

    override fun onThemeChanged(theme: String) {
        quizHolder.chosenTheme = theme
    }
}