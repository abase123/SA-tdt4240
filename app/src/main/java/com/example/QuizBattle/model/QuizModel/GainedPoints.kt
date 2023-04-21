package com.example.QuizBattle.model.QuizModel

class GainedPoints(private var score:Int=0,private var numCorrectAnswer:Int=0 ) {
    fun addPoints(point:Int){
        score += point
    }
    fun getScore():Int{
        return score
    }
    fun resetPoint(){
        score = 0
    }

    fun incrementNumCorrectAnswer(){
        numCorrectAnswer++
    }

    fun resetNumCorrectAnswer(){
        numCorrectAnswer=0
    }

    fun getNumCorrectAnswer():Int{
        return numCorrectAnswer
    }

}