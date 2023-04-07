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

    lateinit var currStage: QuizStage
    private var questionIndex: Int = 0
    private val questions = quizHolder.quiz.getQuestions()
    private lateinit var activeQuestion: Question
    private lateinit var quizViewModel: QuizViewModel
    lateinit var choseOption:String
    enum class QuizStage {
        PRESENT_QUESTION,
        CHECK_ANSWER,
        SHOW_RESULTS
    }

    override fun handle(context: GameController) {
        setViewModel(context)
        currStage = QuizStage.PRESENT_QUESTION
        quizLoop()
    }

    fun quizLoop() {
        when (currStage) {
            QuizStage.PRESENT_QUESTION -> presentQuestion()
            QuizStage.CHECK_ANSWER -> checkAnswer()
            QuizStage.SHOW_RESULTS -> showResults()
        }
    }

    private fun setViewModel(context: GameController) {
        val currentFragment = getCurrentFragment(context)
        quizViewModel = ViewModelProvider(currentFragment.requireActivity())[QuizViewModel::class.java]
        quizViewModel.dailyQuiz = this
    }

    private fun showResults() {
    }
    private fun checkAnswer() {
        // logic for checking question
        // go to next question
        getNextQuestion()
    }

    private fun presentQuestion() {
        activeQuestion = questions[questionIndex]
        quizViewModel.updateQuestion(activeQuestion)
    }
    private fun getNextQuestion(){
        questionIndex++
        if(questionIndex==questions.size){
           return
        }
        else
            presentQuestion()
    }
    private fun getCurrentFragment(context: GameController): Fragment {
        val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments[0]
    }
}
