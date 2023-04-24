package com.example.QuizBattle.model.PlayerModel

import com.google.firebase.firestore.DocumentSnapshot

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
        userUid=documentSnapshot.getString("userUid") ?: "",
        displayName = documentSnapshot.getString("displayName") ?: "",
        userEmail = documentSnapshot.getString("userEmail") ?: "",
        allTimeScore = documentSnapshot.getLong("allTimeScore")?.toInt() ?: 0,
        dailyQuizTaken = documentSnapshot.getBoolean("dailyQuizTaken") ?: false,
        numQuizzesTaken = documentSnapshot.getLong("numQuizzesTaken")?.toInt() ?: 0
    )

    constructor() : this("","", "", 0, false, 0) {
        // Empty constructor required for Firestore deserialization
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        other as Player
        return userUid == other.userUid
    }

    override fun hashCode(): Int {
        return userUid.hashCode()
    }
}