package com.example.QuizBattle.model.FirestoreRepoes

import com.example.QuizBattle.model.PlayerModel.Player
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FireStoreRepoUser {
    private val db = Firebase.firestore
    private val userCollection = db.collection("Users")
    suspend fun getUser(uid:String)=userCollection.document(uid).get().await()

    suspend fun getAsPlayer(uid: String): Player?{
        val userSnapshot = userCollection.document(uid).get().await()
        return if(userSnapshot.exists()) {
            Player(userSnapshot)
            }  else {null}
        }
    suspend fun addUser(user: Player, uid: String){
        userCollection.document(uid).set(user).await()
    }
    suspend fun getDailyQuizState(uid: String):Boolean{
        val userSnapshot = userCollection.document(uid).get().await()
        return userSnapshot.getBoolean("dailyQuizTaken") as Boolean
    }

    suspend fun upDateDailyQuizState(uid: String,newState:Boolean){
        userCollection.document(uid).update("dailyQuizTaken",newState).await()
    }
    suspend fun updateScore(uid: String, newScore: Int){
        userCollection.document(uid).update("score",newScore).await()
    }

    suspend fun getscore(uid: String): Int?{
        val userSnapshot = userCollection.document(uid).get().await()
        return userSnapshot.getLong("score")?.toInt()
    }

}