package com.example.QuizBattle.controller.statePattern

import com.example.QuizBattle.R
import com.example.QuizBattle.controller.Game

class DailyQuiz:GameState {
    override fun handle(context: Game) {

        context.setContentView(R.layout.question)

    }
}