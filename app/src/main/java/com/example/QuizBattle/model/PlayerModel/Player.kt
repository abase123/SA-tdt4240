package com.example.QuizBattle.model.PlayerModel

import android.service.notification.NotificationListenerService.Ranking
import com.google.android.material.color.utilities.Score
import com.google.firebase.firestore.DocumentSnapshot


class Player(
    var displayName: String,
    var email: String,
    var score: Int = 0,
    var dailyQuizTaken: Boolean = false,
    var numQuizzesTaken: Int =0
) {
    constructor(documentSnapshot: DocumentSnapshot):this(
        displayName = documentSnapshot.getString("displayName") ?: "",
        email = documentSnapshot.getString("email") ?: "",
        score = documentSnapshot.getLong("score")?.toInt() ?: 0,
        dailyQuizTaken = documentSnapshot.getBoolean("dailyQuizTaken") ?: false,
        numQuizzesTaken = documentSnapshot.getLong("numQuizzesTaken")?.toInt() ?: 0
    )
    fun getName():String{
        return displayName
    }
    fun addPoints(point:Int){
        score += point
    }
    fun geScore():Int{
        return score
    }
    fun incrementNumQuizzTaken(){
        numQuizzesTaken+=1
    }
    fun setDailyQuizState(newState:Boolean){
        dailyQuizTaken=newState
    }


}