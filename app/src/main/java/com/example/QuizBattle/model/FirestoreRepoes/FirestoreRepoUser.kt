package com.example.QuizBattle.model.FirestoreRepoes

import com.example.QuizBattle.model.PlayerModel.Player
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirestoreRepoUser {
    private val db = Firebase.firestore
    private val userCollection = db.collection("Users")
    suspend fun getUser(uid:String)=userCollection.document(uid).get().await()

    suspend fun getAsPlayer(uid: String): Player?{
        val userSnapshot = userCollection.document(uid).get().await()
        return if(userSnapshot.exists()) {
            Player(userSnapshot)
            }  else {
                null
            }
        }

    suspend fun updatePlayerData(userUid: String, updates: Map<String, Any>) = suspendCancellableCoroutine<Unit> { continuation ->
        userCollection.document(userUid)
            .update(updates)
            .addOnSuccessListener {
                continuation.resume(Unit)
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }

    suspend fun addUser(user: Player, uid: String){
        userCollection.document(uid).set(user).await()
    }

    suspend fun getUserByName(displayName:String): Task<QuerySnapshot> {
        return db.collection("Users")
            .whereEqualTo("displayName", displayName)
            .get()
    }

    suspend fun searchUsersByEmail(email: String): MutableList<Player> {
        val querySnapshot = userCollection
            .orderBy("userEmail")
            .startAt(email.lowercase())
            .endAt(email.lowercase() + "\uf8ff")
            .get()
            .await()

        val players = mutableListOf<Player>()
        for (document in querySnapshot.documents) {
            val player = document.toObject(Player::class.java)
            if (player != null) {
                players.add(player)
            }
        }
        return players
    }
}