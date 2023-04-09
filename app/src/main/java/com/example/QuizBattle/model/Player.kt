package com.example.QuizBattle.model

import android.service.notification.NotificationListenerService.Ranking
import com.google.android.material.color.utilities.Score

private val rankNames = mapOf(
    "Noob" to (0..20),
    "Smarty_Pants" to (21..50),
    "Brianic" to (51..100),
    "Trivia Titan" to (101..250),
    "Supreme QuizMaster" to (251 ..500))
fun getRankForScore(score: Int): String {
    for ((rank, range) in rankNames) {
        if (score in range) {
            return rank
        }
    }
    return "GO FIND GOD! YOU HAVE MASTERED THE GAME."
}


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