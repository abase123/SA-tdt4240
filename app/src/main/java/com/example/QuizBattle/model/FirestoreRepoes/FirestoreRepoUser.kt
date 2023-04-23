package com.example.QuizBattle.model.FirestoreRepoes

import android.content.ContentValues
import android.util.Log
import com.example.QuizBattle.model.FriendModel.FriendList
import com.example.QuizBattle.model.FriendModel.FriendRequest
import com.example.QuizBattle.model.PlayerModel.Player
import com.google.firebase.firestore.FieldValue
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


    suspend fun getFriendList(uid: String): FriendList? {
        val friendListSnapshot = db.collection("FriendList").document(uid).get().await()
        return if (friendListSnapshot.exists()) {
            friendListSnapshot.toObject(FriendList::class.java)
        } else {
            null
        }
    }

    suspend fun removeFriend(uid: String, friend: Player?) {
        // Remove friend from user's friend list
        val friendListRef = db.collection("FriendList").document(uid)
        friendListRef.update("friendsList", FieldValue.arrayRemove(friend))

        // Remove user from friend's friend list
        val friendId = friend?.userUid
        if (friendId != null) {
            val friendRef = db.collection("FriendList").document(friendId)
            friendRef.update("friendsList", FieldValue.arrayRemove(getAsPlayer(uid)))
        }
    }

    suspend fun updateFriendList(uid: String, friendList: FriendList) {
        db.collection("FriendList").document(uid).set(friendList).await()
    }
    suspend fun getFriendRequests(uid: String): List<FriendRequest>? {
        val querySnapshot = db.collection("friendRequests")
            .whereEqualTo("receiverId", uid)
            .get()
            .await()
        return if (!querySnapshot.isEmpty) {
            querySnapshot.toObjects(FriendRequest::class.java)
        } else {
            null
        }
    }

    suspend fun getAllFriendRequests(): List<FriendRequest>? {
        val querySnapshot = db.collection("friendRequests")
            .get()
            .await()
        return if (!querySnapshot.isEmpty) {
            querySnapshot.toObjects(FriendRequest::class.java)
        } else {
            null
        }
    }

    suspend fun removeFriendRequest(friendRequest: FriendRequest) {
        db.collection("friendRequests")
            .whereEqualTo("receiverId", friendRequest.receiverId)
            .whereEqualTo("senderId", friendRequest.senderId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    document.reference.delete()
                }
            }
            .addOnFailureListener { exception ->
                Log.e(ContentValues.TAG, "Error removing friend request: ", exception)
            }
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

    suspend fun sendFriendRequest(senderId: String, receiverId: String) {
        val friendRequest = FriendRequest(senderId, receiverId)
        db.collection("friendRequests").add(friendRequest).await()
    }

}