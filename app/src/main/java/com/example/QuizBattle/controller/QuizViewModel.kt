package com.example.QuizBattle.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.QuizBattle.controller.gameStates.DailyQuiz
import com.example.QuizBattle.model.QuizModel.Question

class QuizViewModel : ViewModel() {

    private val _currentQuestion = MutableLiveData<Question>()
    private var _quizStage = MutableLiveData<DailyQuiz.QuizStage>()
    private var _chosenOption = MutableLiveData<String>()
    val currentQuestion: LiveData<Question> get() = _currentQuestion
    val quizStage: LiveData<DailyQuiz.QuizStage> get() = _quizStage
    val chosenOption: LiveData<String> get() = _chosenOption
    lateinit var dailyQuiz: DailyQuiz


    fun updateQuestion(question: Question) {
        _currentQuestion.value = question
    }

    fun triggerQuizLoop() {
        dailyQuiz.quizLoop()
    }

    fun updateQuizStage(newQuizStage: DailyQuiz.QuizStage) {
        _quizStage.value = newQuizStage
    }

    fun updateChosenOption(optionText:String){
        dailyQuiz.choseOption = optionText
    }
}
