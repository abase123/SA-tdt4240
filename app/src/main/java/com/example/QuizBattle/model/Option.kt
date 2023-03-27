package com.example.QuizBattle.model

class Option(private val optionText: String, private val isCorrect:Boolean) : QuizComponent{
    private lateinit var optionId:String
    private lateinit var optionType:String

    fun getOptionText():String=optionText
    override fun getType(): String {
        return optionType
    }
    override fun getId(): String {
        return optionId
    }

}