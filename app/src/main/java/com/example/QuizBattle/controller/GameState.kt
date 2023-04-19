package com.example.QuizBattle.controller

import android.content.Context
import com.example.QuizBattle.model.QuizModel.QuizHolder

interface GameState {
   fun handleState(context: GameController)
   var quizHolder: QuizHolder
}