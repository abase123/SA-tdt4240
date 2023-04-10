package com.example.QuizBattle.model

import android.service.notification.NotificationListenerService.Ranking
import com.google.android.material.color.utilities.Score




class Player(
    var displayName: String,
    var email: String,
    var score: Int = 0,
    var dailyQuizTaken: Boolean = false,
    var ranking: String
) {
    fun addPoints(point:Int){
        score += point
    }
    fun geScore():Int{
        return score
    }


}