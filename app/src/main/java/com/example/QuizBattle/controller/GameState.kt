package com.example.QuizBattle.controller

import com.example.QuizBattle.model.QuizModel.QuizHolder

interface GameState {
   fun handleState(context: GameController)
   var quizHolder: QuizHolder
}