package com.example.QuizBattle.controller.gameStates.SelectTheme

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameActivity
import com.example.QuizBattle.controller.GameCoreController.GameState
import com.example.QuizBattle.view.ChooseThemeView
import com.example.QuizBattle.model.QuizModel.QuizHolder

/**

SelectTheme is a class responsible for handling the state of selecting a quiz theme.
It implements the GameState interface and listens for theme changes using the
com.example.QuizBattle.controller.gameStates.SelectTheme.OnThemeSelectListener interface. This class updates the quiz holder with the
chosen theme and interacts with the UI to allow the user to select a theme.
@property quizHolder Holds the current quiz data, including the chosen theme.

 */

class SelectTheme(override var quizHolder: QuizHolder) : GameState, OnThemeSelectListener {
    override fun handleState(context: GameActivity) {
        val currentFragment = getCurrentFragment(context) as ChooseThemeView
        currentFragment.themeChangeListener = this
    }

    override fun getCurrentFragment(context: GameActivity): Fragment {
        val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments[0]
    }

    override fun onThemeSelected(theme: String) {
        quizHolder.chosenTheme = theme
    }
}