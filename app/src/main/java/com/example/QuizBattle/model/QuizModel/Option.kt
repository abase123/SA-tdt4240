package com.example.QuizBattle.model.QuizModel

class Option(private val optionText: String) : QuizComponent {
    private lateinit var optionId:String
    private lateinit var optionType:String

    fun getOptionText():String=optionText
    override fun getId(): String {
        return optionId
    }

}