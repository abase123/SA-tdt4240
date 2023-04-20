package com.example.QuizBattle.controller.gameStates

import androidx.lifecycle.lifecycleScope
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.controller.GameState
import com.example.QuizBattle.controller.PlayerViewModel
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoMatch
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoQuiz
import com.example.QuizBattle.model.QuizModel.QuizHolder
import kotlinx.coroutines.launch

class MatchMaking(override var quizHolder: QuizHolder,private val playerViewModel: PlayerViewModel) : GameState {

    private val repoOnlineQuiz: FirestoreRepoQuiz = FirestoreRepoQuiz("battle_quizzes")
    private val repoMatchMaking: FirestoreRepoMatch = FirestoreRepoMatch()
    override fun handleState(context: GameController) {
        val player= playerViewModel.player.value
        context.lifecycleScope.launch {
            val match = player?.let { repoMatchMaking.joinOrCreateGame(it) }
            println("Joined existing game: $match")
        }


    }




}