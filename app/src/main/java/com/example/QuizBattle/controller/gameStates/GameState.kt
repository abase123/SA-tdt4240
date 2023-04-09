package com.example.QuizBattle.controller.gameStates

import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.model.QuizModel.QuizHolder

interface GameState {
   fun handle(context: GameController)
   var quizHolder: QuizHolder
}