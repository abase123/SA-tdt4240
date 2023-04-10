package com.example.QuizBattle.controller.gameStates

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.QuizBattle.FireBaseRepoUser
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.controller.QuizViewModel
import com.example.QuizBattle.model.QuizModel.GainedPoints
import com.example.QuizBattle.model.QuizModel.QuizHolder
import com.example.QuizBattle.model.getRankForScore
import com.example.QuizBattle.view.LoadingQuizView
import com.example.QuizBattle.view.ResultsView
import com.google.android.material.color.utilities.Score
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class PresentQuizResults(override var quizHolder: QuizHolder) :GameState {
    private val fireBaseRepoUser:FireBaseRepoUser = FireBaseRepoUser()
    private val mAuth = FirebaseAuth.getInstance()

    override fun handle(context: GameController) {
        val currentUser=mAuth.currentUser
        if (currentUser != null){
            val uid=currentUser.uid
            context.lifecycleScope.launch{
                val isDailyQuizTaken=fireBaseRepoUser.getDailyQuizState(uid)
                if(!isDailyQuizTaken){
                    val newScore=quizHolder.gainedPoints.getScore()
                    fireBaseRepoUser.updateScore(uid,newScore)
                    val newRank= getRankForScore(newScore)
                    fireBaseRepoUser.updateRank(uid,newRank)
                    fireBaseRepoUser.upDateDailyQuizState(uid,true)
                    showResults(newScore,newRank,quizHolder.gainedPoints,context)
                }
                else{
                    val oldRank=fireBaseRepoUser.getRank(uid)
                    val score =fireBaseRepoUser.getscore(uid)
                    showResults(score,oldRank,quizHolder.gainedPoints,context)
                }
            }
        }
    }

    private fun showResults(score:Int? ,newRank:String,pointsGained:GainedPoints,context: GameController){
        val currentFragment=getCurrentFragment(context)
        (currentFragment as? ResultsView)?.presentQuizResults(score,newRank,pointsGained)
    }

    private fun getCurrentFragment(context: GameController): Fragment {
        val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments[0]
    }


}