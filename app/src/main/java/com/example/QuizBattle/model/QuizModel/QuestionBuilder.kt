package com.example.QuizBattle.model.QuizModel

import com.example.QuizBattle.model.QuizModel.MainQuizComponents.Option
import com.example.QuizBattle.model.QuizModel.MainQuizComponents.Question
/**

A builder class for creating Question objects with options.

@param id The id of the question

@param text The text of the question

@param correct The correct answer for the question
 */
class QuestionBuilder(private val text: String, private val correct: String) {
    private val options = mutableListOf<Option>()

    fun addOption(option: Option): QuestionBuilder {
        options.add(option)
        return this
    }

    fun build(): Question {
        val question = Question(text, correct)
        question.setOptions(options)
        return question
    }
}
