package com.example.QuizBattle.controller.gameStates.PlayQuizState

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.controller.GameState
import com.example.QuizBattle.model.QuizModel.QuizHolder
import com.example.QuizBattle.model.QuizModel.Question

class PlayQuiz(override var quizHolder:QuizHolder) : GameState {
    private var questionIndex: Int = 0
    private val questions = quizHolder.quiz.getQuestions()
    private lateinit var activeQuestion: Question
    private lateinit var playDailyQuizViewModel: PlayQuizViewModel
    private var quizEnded=false

    override fun handleState(context: GameController) {
        quizEnded=false
        quizHolder.gainedPoints.resetPoint()
        quizHolder.gainedPoints.resetNumCorrectAnswer()
        questionIndex=0
        quizHolder.timer.initTimer()
        setViewModel(context)
        presentQuestion()
    }

    private fun setViewModel(context: GameController) {
        val currentFragment = getCurrentFragment(context)
        playDailyQuizViewModel = ViewModelProvider(currentFragment.requireActivity())[PlayQuizViewModel::class.java]
        playDailyQuizViewModel.dailyQuiz = this
        playDailyQuizViewModel.endQuiz(quizEnded)
    }


    private fun calTimePenalty():Float{
        val timeUsed=quizHolder.timer.getElapsedTime()
        Log.d("timeUsed", "Quiz accessed: $timeUsed")
        val penalty= (10-timeUsed) / 10.0f
        if (penalty <= 0){
            return 0f
        }
        return penalty
    }
    fun checkAnswer(choseOption:String):Boolean{
        val isCorrectOption=choseOption==activeQuestion.getCorrectAnswer()
        val timePenalty = calTimePenalty()
        val pointsToAdd= (200f * timePenalty)
        quizHolder.timer.resetTimer()
        if(isCorrectOption){
            quizHolder.gainedPoints.addPoints(pointsToAdd.toInt())
            quizHolder.gainedPoints.incrementNumCorrectAnswer()
        }
        return isCorrectOption
    }

    private fun presentQuestion() {
        quizHolder.timer.startTimer()
        activeQuestion = questions[questionIndex]
        playDailyQuizViewModel.updateQuestion(activeQuestion)
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
        playDailyQuizViewModel.endQuiz(quizEnded)
    }

    private fun getCurrentFragment(context: GameController): Fragment {
        val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments[0]
    }
}

