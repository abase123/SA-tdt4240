package com.example.QuizBattle.model.QuizModel


class Question(@JvmField val id: String, val text: String, private val correct: String) : QuizComponent {
    private var questionId: String
    private var questionText: String
    private var options: MutableList<Option>  // Initialize the options list
    private var correctAnswer: String = correct

    init {
        questionId = id
        questionText = text
        correctAnswer = correct
        options = mutableListOf()  // Initialize the options list in the init block
    }

    override fun getId(): String = questionId

    fun getQuestionText(): String{
        return questionText
    }
    fun getOptions(): MutableList<Option> = options
    fun addOption(option: Option) {
        options.add(option)
    }

    fun removeOption(option: Option) {
        options.remove(option)
    }

    fun getCorrectAnswer(): String {
        return correctAnswer
    }

}
