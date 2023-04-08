package com.example.QuizBattle.model

class UserScore(private var score: Int =0){
    fun addPoints(point:Int){
        score += point
    }
    fun geScore():Int{
        return score
    }
}