package com.example.QuizBattle.controller.gameStates

import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.model.QuizModel.QuizHolder

interface GameState {
   abstract fun handle(context: GameController)
   abstract var quizHolder: QuizHolder
}