package com.example.QuizBattle.model.PlayerModel

import android.service.notification.NotificationListenerService.Ranking
import com.google.android.material.color.utilities.Score
import com.google.common.reflect.TypeToken
import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.Gson


class Player(
    var userUid:String,
    var displayName: String,
    var userEmail: String,
    var allTimeScore: Int = 0,
    var dailyQuizTaken: Boolean = false,
    var numQuizzesTaken: Int =0
)

{
    constructor(documentSnapshot: DocumentSnapshot):this(
        userUid=documentSnapshot.id,
        displayName = documentSnapshot.getString("displayName") ?: "",
        userEmail = documentSnapshot.getString("email") ?: "",
        allTimeScore = documentSnapshot.getLong("score")?.toInt() ?: 0,
        dailyQuizTaken = documentSnapshot.getBoolean("dailyQuizTaken") ?: false,
        numQuizzesTaken = documentSnapshot.getLong("numQuizzesTaken")?.toInt() ?: 0
    )


    constructor() : this("","", "", 0, false, 0) {
        // Empty constructor required for Firestore deserialization
    }
}