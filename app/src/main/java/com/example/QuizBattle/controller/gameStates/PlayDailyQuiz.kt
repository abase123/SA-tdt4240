package com.example.QuizBattle.controller.gameStates

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.model.QuizModel.QuizHolder
import com.example.QuizBattle.controller.QuizViewModel
import com.example.QuizBattle.model.QuizModel.Question
class PlayDailyQuiz(override var quizHolder:QuizHolder) : GameState {

    private var questionIndex: Int = 0
    private val questions = quizHolder.quiz.getQuestions()
    private lateinit var activeQuestion: Question
    private lateinit var quizViewModel: QuizViewModel
    private var quizEnded=false

    override fun handle(context: GameController) {
        quizEnded=false
        quizHolder.gainedPoints.resetPoint()
        questionIndex=0
        setViewModel(context)
        presentQuestion()
    }

    private fun setViewModel(context: GameController) {
        val currentFragment = getCurrentFragment(context)
        quizViewModel = ViewModelProvider(currentFragment.requireActivity())[QuizViewModel::class.java]
        quizViewModel.dailyQuiz = this
        quizViewModel.endQuiz(quizEnded)
    }

    fun checkAnswer(choseOption:String):Boolean{
        val isCorrectOption=choseOption==activeQuestion.getCorrectAnswer()
        if(isCorrectOption){
            quizHolder.gainedPoints.addPoints(1)
            Log.d("Points", "Quiz accessed: ${quizHolder.gainedPoints.getScore()}")
        }
        return isCorrectOption
    }
    private fun presentQuestion() {
        activeQuestion = questions[questionIndex]
        quizViewModel.updateQuestion(activeQuestion)
    }
    fun getNextQuestion(){
        questionIndex++
        if(questionIndex==questions.size){
            endQuiz()
        }
        else
            presentQuestion()
    }
    private fun endQuiz() {
        quizEnded=true
        questionIndex=0
        quizViewModel.endQuiz(quizEnded)
    }

    private fun getCurrentFragment(context: GameController): Fragment {
        val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments[0]
    }
}

