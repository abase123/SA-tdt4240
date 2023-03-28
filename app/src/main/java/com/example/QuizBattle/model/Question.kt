package com.example.QuizBattle.model

import java.util.Locale.Category


class Question {

    private lateinit var questionText: String
    private lateinit var answerAlternatives:List<String>
    private lateinit var questionCategory:String
    private var questionScore:Float= 100.0F
    fun createQuestion(){
        questionText="Which of the following was not one of &quot;The Magnificent Seven&quot;?"


    }



}