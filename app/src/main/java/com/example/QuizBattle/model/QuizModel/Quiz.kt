package com.example.QuizBattle.model.QuizModel



import com.example.QuizBattle.model.QuizModel.Question
import com.example.QuizBattle.model.QuizModel.QuizComponent

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
    fun getQuestions():MutableList<Question>{
        return questions
    }

    override fun toString(): String {
        return "Quiz(type='$quizType', id='$quizId', difficulty='$quizDifficulty', theme='$quizTheme')"
    }

}