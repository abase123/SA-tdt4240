package com.example.QuizBattle.model

import android.graphics.BitmapFactory.Options
import java.util.Locale.Category


class Question(private val questionText: String):QuizComponent{
    private lateinit var questionId:String
    private lateinit var questionCategory: String
    private lateinit var options:MutableList<Option>

    fun getQuestionTex():String=questionText
    fun getOptions(): MutableList<Option> =options
    fun addOption(option: Option){
        options.add(option)
    }

    fun removeOption(option: Option){
        options.remove(option)
    }


    override fun getType(): String {
        return questionCategory
    }

    override fun getId(): String {
        return questionId
    }


}