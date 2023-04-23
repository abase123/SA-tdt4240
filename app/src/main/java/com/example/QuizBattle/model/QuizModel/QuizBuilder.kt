package com.example.QuizBattle.model.QuizModel

import com.example.QuizBattle.model.QuizModel.MainQuizComponents.Question
import com.example.QuizBattle.model.QuizModel.MainQuizComponents.Quiz

class QuizBuilder(private val type: String, private val id: String, private val difficulty: String, private val theme: String) {
    private val questions = mutableListOf<Question>()

    fun build(): Quiz {
        val quiz = Quiz(type, id, difficulty, theme)
        quiz.setQuestions(questions)
        return quiz
    }
}
