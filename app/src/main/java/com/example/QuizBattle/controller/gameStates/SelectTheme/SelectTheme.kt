package com.example.QuizBattle.controller.gameStates.SelectTheme

import androidx.lifecycle.Observer
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.controller.GameState
import com.example.QuizBattle.model.QuizModel.QuizHolder

class SelectTheme(override var quizHolder: QuizHolder) :GameState {
    val categoryViewModel = SelectThemeViewModel()

    private val selectCategoryObserver = Observer<String> { value ->
        quizHolder.chosenTheme = value
    }
    init {
        categoryViewModel.categoryViewModel.observeForever(selectCategoryObserver)
    }
    override fun handleState(context: GameController) {

    }
}