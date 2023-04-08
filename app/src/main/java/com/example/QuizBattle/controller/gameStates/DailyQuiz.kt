package com.example.QuizBattle.controller.gameStates

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.model.QuizModel.QuizHolder
import com.example.QuizBattle.controller.QuizViewModel
import com.example.QuizBattle.model.QuizModel.Question

class DailyQuiz(override var quizHolder: QuizHolder) : GameState {

    private var questionIndex: Int = 0
    private val questions = quizHolder.quiz.getQuestions()
    private lateinit var activeQuestion: Question
    private lateinit var quizViewModel: QuizViewModel

    override fun handle(context: GameController) {
        setViewModel(context)
        presentQuestion()
    }
    private fun setViewModel(context: GameController) {
        val currentFragment = getCurrentFragment(context)
        quizViewModel = ViewModelProvider(currentFragment.requireActivity())[QuizViewModel::class.java]
        quizViewModel.dailyQuiz = this
    }


    fun checkAnswer(choseOption:String):Boolean{
        /// visitor here before feedback.
        return choseOption==activeQuestion.getCorrectAnswer()
    }
    private fun presentQuestion() {
        activeQuestion = questions[questionIndex]
        quizViewModel.updateQuestion(activeQuestion)
    }

    fun getNextQuestion(){
        questionIndex++
        if(questionIndex==questions.size){
            showFinalResults()
        }
        else
            presentQuestion()
    }


    private fun showFinalResults() {

    }
    private fun getCurrentFragment(context: GameController): Fragment {
        val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments[0]
    }
}
