package com.example.QuizBattle.controller.gameStates.PlayDailyQuizState

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.QuizBattle.model.QuizModel.Question

class PlayDailyQuizViewModel : ViewModel() {

    private val _currentQuestion = MutableLiveData<Question>()
    lateinit var dailyQuiz: PlayDailyQuiz
    private var _isCorrectAnswer = MutableLiveData<Boolean>()
    val currentQuestion: LiveData<Question> get() = _currentQuestion
    private val _quizEnded = MutableLiveData<Boolean>()
    val quizEnded: LiveData<Boolean> get() = _quizEnded

    fun setIsCorrectAnswer(value: Boolean) {
        _isCorrectAnswer.value = value
    }

    fun updateQuestion(question: Question) {
        _currentQuestion.value = question
    }

    fun endQuiz(endQuiz:Boolean){
        _quizEnded.value=endQuiz
    }

}