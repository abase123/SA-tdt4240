package com.example.QuizBattle.controller.gameStates

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameActivity
import com.example.QuizBattle.controller.GameCoreController.GameState
import com.example.QuizBattle.controller.PlayerViewModel
import com.example.QuizBattle.model.PlayerModel.Player
import com.example.QuizBattle.model.QuizModel.GainedPoints
import com.example.QuizBattle.model.QuizModel.QuizHolder
import com.example.QuizBattle.view.ResultsView

/**
 * HandleQuizResults is a class responsible for handling and updating the quiz results
 * after a quiz is completed. It is used for both daily and playground quizzes.
 * The class is responsible for updating player statistics, presenting the results
 * to the user, and updating the player data in Firestore if necessary.
 *
 * @property quizHolder Holds the current quiz data, including gained points and timer.
 * @property playerViewModel ViewModel instance to interact with the player data and Firestore.
 */
class HandleQuizResults(override var quizHolder: QuizHolder, private var playerViewModel: PlayerViewModel) : GameState {

    override fun handleState(context: GameActivity) {
        val currentPlayer= playerViewModel.player.value

        if (currentPlayer!=null){
            if (quizHolder.quiz.getType()=="Daily"){
                handleDailyResults(context,currentPlayer)
            }
            showResults( quizHolder.gainedPoints, context)
        }
    }

    private fun handleDailyResults(context: GameActivity, player: Player){
        if (!player.dailyQuizTaken) {
            player.dailyQuizTaken=true
            player.allTimeScore=player.allTimeScore+quizHolder.gainedPoints.getPoints()
            player.numQuizzesTaken+=1
            updateFireStore(player)
            showResults(quizHolder.gainedPoints, context)
        } else {
            showResults(quizHolder.gainedPoints, context)
        }
    }

    private fun updateFireStore(player: Player){
       playerViewModel.updatePlayerDataInFirestore(player)
    }
    private fun showResults(pointsGained:GainedPoints,context: GameActivity){
        val currentFragment=getCurrentFragment(context)
        (currentFragment as? ResultsView)?.presentQuizResults(pointsGained,quizHolder.timer.getTotalTimeUsed(), quizHolder.quiz.getQuestionsLength())
    }

    override fun getCurrentFragment(context: GameActivity): Fragment {
        val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments[0]
    }


}