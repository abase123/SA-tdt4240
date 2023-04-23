package com.example.QuizBattle.controller.gameStates.PlayQuizState

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.QuizBattle.model.QuizModel.MainQuizComponents.Question
/**

PlayQuizViewModel is a ViewModel class for managing the UI state during the PlayQuiz game state.

It holds the current question, the quizEnded flag, and a LiveData object for checking if the answer is correct.

@property _currentQuestion The MutableLiveData object representing the current question.

@property dailyQuiz The PlayQuiz object that is being used to manage the quiz state.

@property _isCorrectAnswer The MutableLiveData object representing if the chosen answer is correct.

@property currentQuestion The LiveData object representing the current question.

@property _quizEnded The MutableLiveData object representing if the quiz has ended.

@property quizEnded The LiveData object representing if the quiz has ended.
 */
class PlayQuizViewModel : ViewModel() {

    private val _currentQuestion = MutableLiveData<Question>()
    lateinit var dailyQuiz: PlayQuiz
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
