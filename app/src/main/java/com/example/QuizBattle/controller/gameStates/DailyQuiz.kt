package com.example.QuizBattle.controller.gameStates

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.controller.QuizHolder
import com.example.QuizBattle.controller.QuizViewModel
import com.example.QuizBattle.model.Option
import com.example.QuizBattle.model.Question
import com.example.QuizBattle.view.QuizView

class DailyQuiz(override var quizHolder: QuizHolder):GameState{


    override fun handle(context: GameController) {
        val questions:MutableList<Question> = quizHolder.quiz.getQuestions()
        val question=questions[0]
        val currentFragment = getCurrentFragment(context)
        val quizViewModel = ViewModelProvider(currentFragment.requireActivity())[QuizViewModel::class.java]
        quizViewModel.updateQuestion(question)
    }
    private fun getCurrentFragment(context: GameController): Fragment {
        val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        Log.d("Frag", "Quiz accessed: ${navHostFragment.childFragmentManager.fragments}")
        return navHostFragment.childFragmentManager.fragments[0]
    }

}