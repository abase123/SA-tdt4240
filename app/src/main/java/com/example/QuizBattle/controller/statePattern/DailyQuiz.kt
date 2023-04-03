package com.example.QuizBattle.controller.statePattern

import androidx.navigation.fragment.NavHostFragment
import com.example.QuizBattle.FirebaseRepo
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.Game
import com.example.QuizBattle.model.Quiz
import com.example.QuizBattle.view.LoadingQuiz

class DailyQuiz:GameState,DataLoadListener {
    private lateinit var dailyQuiz:Quiz

    override fun handle(context: Game) {
        context.setContentView(R.layout.main_activity)
        val fragmentTransaction = context.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.loadingQuiz, LoadingQuiz())
        fragmentTransaction.commit()

        val firebaseRepo=FirebaseRepo(this)

    }

    override fun onDataLoaded(quiz: Quiz) {
        dailyQuiz=quiz

    }
}
