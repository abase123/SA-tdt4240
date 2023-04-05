package com.example.QuizBattle.model



import java.text.SimpleDateFormat
import java.util.*
// Composite Class: Quiz
class Quiz(type:String, id:String, difficulty:String,theme:String): QuizComponent {
    private  var quizType:String
    private  var quizId:String
    private lateinit var questions: MutableList<Question>
    private  var quizDifficulty:String
    private  var quizTheme:String

    init {
        quizId=id
        quizDifficulty=difficulty
        quizType=type
        quizTheme=theme
        questions= mutableListOf()
    }

    fun setQuestions(newQuestions:MutableList<Question>){
        questions=newQuestions
    }
    fun addQuestion(question: Question){
        questions.add(question)
    }
    fun removeQuestion(question: Question){
        questions.remove(question)
    }
    fun getType(): String {
        return quizType
    }

    fun getTheme():String{
        return quizTheme
    }
    override fun getId(): String {
        return quizId
    }

}