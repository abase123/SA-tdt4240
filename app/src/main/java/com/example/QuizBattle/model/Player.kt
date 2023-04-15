package com.example.QuizBattle.model

import android.service.notification.NotificationListenerService.Ranking
import com.google.android.material.color.utilities.Score
import com.google.firebase.firestore.auth.User


class Player(
    var displayName: String,
    var email: String,
    var score: Int = 0,
    var dailyQuizTaken: Boolean = false,
    var ranking: String,
    var friends: MutableList<Player>
) {
    fun addPoints(point:Int){
        score += point
    }
    fun geScore():Int{
        return score
    }
    fun addFriend(player: Player) {
        if (!friends.contains(player)) {
            friends.add(player)
        }
    }

    fun removeFriend(player: Player) {
        friends.remove(player)
    }

    @JvmName("getFriends1")
    fun getFriends(): List<Player> {
        return friends
    }
}