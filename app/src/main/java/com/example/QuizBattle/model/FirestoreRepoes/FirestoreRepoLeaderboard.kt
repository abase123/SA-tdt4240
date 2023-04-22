package com.example.QuizBattle.model.FirestoreRepoes

import com.example.QuizBattle.model.PlayerModel.Player
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
/**

FirestoreRepoLeaderboard is a class that handles retrieving the top players from the Firestore "Users" collection,
sorted by their all-time scores in descending order. It provides a getTopPlayers function to fetch the top players,
allowing a limit to be specified for the number of players to be returned.
 */

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