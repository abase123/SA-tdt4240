package com.example.QuizBattle.model.QuizModel.MainQuizComponents


class Question(@JvmField val id: String, val text: String, private val correct: String)  {
    private var questionId: String = id
    private var questionText: String = text
    private var options: MutableList<Option>  // Initialize the options list
    private var correctAnswer: String = correct

    init {
        correctAnswer = correct
        options = mutableListOf()  // Initialize the options list in the init block
    }


    fun getQuestionText(): String{
        return questionText
    }
    fun getOptions(): MutableList<Option> = options


    fun getCorrectAnswer(): String {
        return correctAnswer
    }
    fun setOptions(options: MutableList<Option>) {
        this.options = options
    }
}
