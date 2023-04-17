package com.example.QuizBattle.model.PlayerModel

import com.google.firebase.firestore.DocumentSnapshot


data class Player(
     var userUid:String,
     var displayName: String,
     var userEmail: String,
     var allTimeScore: Int = 0,
     var dailyQuizTaken: Boolean = false,
     var numQuizzesTaken: Int =0
) {
    constructor(documentSnapshot: DocumentSnapshot):this(
        userUid =documentSnapshot.id,
        displayName = documentSnapshot.getString("displayName") ?: "",
        userEmail = documentSnapshot.getString("email") ?: "",
        allTimeScore = documentSnapshot.getLong("score")?.toInt() ?: 0,
        dailyQuizTaken = documentSnapshot.getBoolean("dailyQuizTaken") ?: false,
        numQuizzesTaken = documentSnapshot.getLong("numQuizzesTaken")?.toInt() ?: 0
    )

}