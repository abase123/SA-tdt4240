package com.example.QuizBattle.model.QuizModel.MainQuizComponents


// Composite Class: Quiz
class Quiz(type:String, id:String, difficulty:String,theme:String) {
    private  var quizType:String
    private  var quizId:String
    private  var questions: MutableList<Question>
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
    fun getTheme():String{
        return quizTheme
    }
    fun getType():String{
        return quizType
    }

    fun getDiff():String{
        return quizDifficulty
    }
    fun getId(): String {
        return quizId
    }
    fun getQuestions():MutableList<Question>{
        return questions
    }

    fun getQuestionsLength():Int {
        return questions.size
    }

    override fun toString(): String {
        return "Quiz(type='$quizType', id='$quizId', difficulty='$quizDifficulty', theme='$quizTheme')"
    }

}