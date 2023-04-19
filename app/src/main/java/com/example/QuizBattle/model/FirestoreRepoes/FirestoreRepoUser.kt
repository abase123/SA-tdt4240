package com.example.QuizBattle.model.FirestoreRepoes

import android.util.Log
import com.example.QuizBattle.model.PlayerModel.Player
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



}