package com.example.QuizBattle.model

import java.io.File
// Composite Class: Quiz
class Quiz: QuizComponent{
    private lateinit var quizType:String
    private lateinit var quizId:String
    private lateinit var questions: MutableList<Question>

   suspend fun loadQuestions(){return}

    fun addQuestion(question: Question){
        questions.add(question)
    }
    fun removeQuestion(question: Question){
        questions.remove(question)
    }
    override fun getType(): String {
        return quizType
    }
    override fun getId(): String {
        return quizId
    }


}