package com.example.QuizBattle.controller.gameStates

import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.controller.GameState
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoQuiz
import com.example.QuizBattle.model.QuizModel.QuizHolder

class MatchMaking(override var quizHolder: QuizHolder) : GameState {

    private val repoOnlineQuiz: FirestoreRepoQuiz = FirestoreRepoQuiz("battle_quizzes")
    override fun handleState(context: GameController) {
        return
    }

}