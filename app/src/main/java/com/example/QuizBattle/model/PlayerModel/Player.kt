package com.example.QuizBattle.model.PlayerModel

import com.google.firebase.firestore.DocumentSnapshot
/**
 * The Player class represents a user within the QuizBattle application. It stores important
 * information about the user, such as their unique user ID, display name, email address,
 * all-time score, daily quiz participation, and the number of quizzes they've taken.
 *
 * This class includes multiple constructors for different use cases, such as creating a
 * Player object from a DocumentSnapshot (for Firestore deserialization), an empty constructor
 * for Firestore deserialization, and a primary constructor for general use.
 */
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