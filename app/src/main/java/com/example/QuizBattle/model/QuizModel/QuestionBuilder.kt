package com.example.QuizBattle.model.QuizModel

import com.example.QuizBattle.model.QuizModel.MainQuizComponents.Option
import com.example.QuizBattle.model.QuizModel.MainQuizComponents.Question

class QuestionBuilder(private val id: String, private val text: String, private val correct: String) {
    private val options = mutableListOf<Option>()

    fun addOption(option: Option): QuestionBuilder {
        options.add(option)
        return this
    }

    fun build(): Question {
        val question = Question(id, text, correct)
        question.setOptions(options)
        return question
    }
}
