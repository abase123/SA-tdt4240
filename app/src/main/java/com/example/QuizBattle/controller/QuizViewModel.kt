package com.example.QuizBattle.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.QuizBattle.controller.gameStates.DailyQuiz
import com.example.QuizBattle.model.QuizModel.Question

class QuizViewModel : ViewModel() {
    private val _currentQuestion = MutableLiveData<Question>()
    lateinit var dailyQuiz: DailyQuiz
    private var _isCorrectAnswer = MutableLiveData<Boolean>()
    val currentQuestion: LiveData<Question> get() = _currentQuestion
    val isCorrectAnswer: LiveData<Boolean> get() = _isCorrectAnswer

    fun setIsCorrectAnswer(value: Boolean) {
        _isCorrectAnswer.value = value
    }

    fun updateQuestion(question: Question) {
        _currentQuestion.value = question
    }

    fun updateChosenOption(optionText:String){
        dailyQuiz.checkAnswer(optionText)
    }


}
