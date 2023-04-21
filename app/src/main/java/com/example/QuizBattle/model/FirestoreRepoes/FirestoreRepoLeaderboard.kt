package com.example.QuizBattle.model.FirestoreRepoes

import com.example.QuizBattle.model.PlayerModel.Player
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirestoreRepoLeaderboard {
    private val db = Firebase.firestore
    private val userCollection = db.collection("Users")
    suspend fun getTopPlayers(limit: Int = 10): List<Player> {
        val querySnapshot = userCollection
            .orderBy("allTimeScore", Query.Direction.DESCENDING)
            .limit(limit.toLong())
            .get()
            .await()

        return querySnapshot.documents.mapNotNull { documentSnapshot ->
            if (documentSnapshot.exists()) {
                Player(documentSnapshot)
            } else {
                null
            }
        }
    }

}