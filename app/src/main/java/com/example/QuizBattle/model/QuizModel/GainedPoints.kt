package com.example.QuizBattle.model.QuizModel

class GainedPoints(private var score:Int=0) {
    fun addPoints(point:Int){
        score += point
    }
    fun getScore():Int{
        return score
    }
    fun resetPoint(){
        score = 0
    }

}