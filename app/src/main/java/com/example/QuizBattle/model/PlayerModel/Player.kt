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
    var numQuizzesTaken: Int =0,
    var friends: MutableList<Player>
)

{
    constructor(documentSnapshot: DocumentSnapshot):this(
        userUid=documentSnapshot.id,
        displayName = documentSnapshot.getString("displayName") ?: "",
        userEmail = documentSnapshot.getString("email") ?: "",
        allTimeScore = documentSnapshot.getLong("score")?.toInt() ?: 0,
        dailyQuizTaken = documentSnapshot.getBoolean("dailyQuizTaken") ?: false,
        numQuizzesTaken = documentSnapshot.getLong("numQuizzesTaken")?.toInt() ?: 0,
        friends = mutableListOf()
    )


    constructor() : this("","", "", 0, false, 0, mutableListOf()) {
        // Empty constructor required for Firestore deserialization
    }
    constructor(json: String) : this(
        userUid ="",
        displayName = "",
        userEmail = "",
        friends = Gson().fromJson(json, object : TypeToken<MutableList<Player>>() {}.type),
        allTimeScore = 0,
        dailyQuizTaken = false,
        numQuizzesTaken = 0
    )

    // Serialize the MutableList<Player> object to a JSON string
    fun toJson(): String {
        return Gson().toJson(this.friends)
    }


}