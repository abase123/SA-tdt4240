package com.example.QuizBattle.model.QuizModel

import com.example.QuizBattle.model.QuizModel.MainQuizComponents.Question
import com.example.QuizBattle.model.QuizModel.MainQuizComponents.Quiz

/**

A class responsible for building a quiz by creating a list of questions.

@param type the type of the quiz

@param id the unique ID of the quiz

@param difficulty the difficulty level of the quiz

@param theme the theme of the quiz
 */
class QuizBuilder(private val type: String, private val id: String, private val difficulty: String, private val theme: String) {
    private val questions = mutableListOf<Question>()

    fun build(): Quiz {
        val quiz = Quiz(type, id, difficulty, theme)
        quiz.setQuestions(questions)
        return quiz
    }
}
