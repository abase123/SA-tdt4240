package com.example.QuizBattle.controller.gameStates

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameActivity
import com.example.QuizBattle.controller.GameState
import com.example.QuizBattle.controller.PlayerViewModel
import com.example.QuizBattle.model.PlayerModel.Player
import com.example.QuizBattle.model.QuizModel.GainedPoints
import com.example.QuizBattle.model.QuizModel.QuizHolder
import com.example.QuizBattle.view.ResultsView

class PresentQuizResults(override var quizHolder: QuizHolder, private var playerViewModel: PlayerViewModel) : GameState {

    override fun handleState(context: GameActivity) {
        val player= playerViewModel.player.value

        if (player!=null){
            if (quizHolder.quiz.getType()=="Daily"){
                handleDailyResults(context,player)
            }
            showResults(player.allTimeScore, quizHolder.gainedPoints, context)
        }
    }

    private fun handleDailyResults(context: GameActivity, player: Player){
        if (!player.dailyQuizTaken) {
            player.dailyQuizTaken=true
            player.allTimeScore=player.allTimeScore+quizHolder.gainedPoints.getScore()
            player.numQuizzesTaken+=1
            updateFireStore(player)
            showResults(player.allTimeScore, quizHolder.gainedPoints, context)
        } else {
            showResults(player.allTimeScore, quizHolder.gainedPoints, context)
        }
    }

    private fun updateFireStore(player: Player){
       playerViewModel.updatePlayerDataInFirestore(player)
    }
    private fun showResults(score:Int ,pointsGained:GainedPoints,context: GameActivity){
        val currentFragment=getCurrentFragment(context)
        (currentFragment as? ResultsView)?.presentQuizResults(pointsGained,quizHolder.timer.getTotalTimeUsed(), quizHolder.quiz.getQuestionsLength())
    }

    private fun getCurrentFragment(context: GameActivity): Fragment {
        val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments[0]
    }


}