package com.example.QuizBattle.controller.gameStates

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.controller.GameState
import com.example.QuizBattle.model.PlayerModel.Player
import com.example.QuizBattle.model.QuizModel.GainedPoints
import com.example.QuizBattle.model.QuizModel.QuizHolder
import com.example.QuizBattle.views.ResultsView
import kotlinx.coroutines.launch
import kotlin.math.log

class PresentQuizResults(override var quizHolder: QuizHolder) : GameState {

    override fun handle(context: GameController) {
        if (context.player != null) {
            val player = context.player!!
            if (!player.dailyQuizTaken) {
                val newScore = player.score + quizHolder.gainedPoints.getScore()
                player.dailyQuizTaken = true
                player.score = newScore
                player.numQuizzesTaken=player.numQuizzesTaken+1
                updateFireStore(context,player,newScore,true)
                showResults(newScore, quizHolder.gainedPoints, context)
            } else {
                showResults(player.score, quizHolder.gainedPoints, context)
            }
        }
    }

    private fun updateFireStore(context: GameController, player: Player, newScore:Int, newDailyQuizState:Boolean){
        context.lifecycleScope.launch {
            context.fireStoreRepoUser.updateScore(player.email, newScore)
            context.fireStoreRepoUser.upDateDailyQuizState(player.email, true)
        }
    }
    private fun showResults(score:Int ,pointsGained:GainedPoints,context: GameController){
        val currentFragment=getCurrentFragment(context)
        (currentFragment as? ResultsView)?.presentQuizResults(score,pointsGained,quizHolder.timer.getTotalTimeUsed())
        Log.d("plis","fkefkeofkef")
    }

    private fun getCurrentFragment(context: GameController): Fragment {
        val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments[0]
    }


}