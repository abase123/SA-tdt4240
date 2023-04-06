package com.example.QuizBattle.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.QuizBattle.model.Question

class QuizViewModel:ViewModel() {
    private val _currentQuestion = MutableLiveData<Question>()
    val currentQuestion: LiveData<Question> get() = _currentQuestion

    fun updateQuestion(question: Question) {
        _currentQuestion.value = question
    }
}