package com.example.QuizBattle.model

import com.example.QuizBattle.model.QuizModel.QuizComponent

class Option(private val optionText: String) : QuizComponent {
    private lateinit var optionId:String
    private lateinit var optionType:String

    fun getOptionText():String=optionText
    override fun getId(): String {
        return optionId
    }

}