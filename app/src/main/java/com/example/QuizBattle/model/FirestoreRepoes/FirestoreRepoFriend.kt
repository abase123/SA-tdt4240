package com.example.QuizBattle.model.FirestoreRepoes

import android.content.ContentValues
import android.util.Log
import com.example.QuizBattle.model.FriendModel.FriendList
import com.example.QuizBattle.model.FriendModel.FriendRequest
import com.example.QuizBattle.model.PlayerModel.Player
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * The FirestoreRepoFriend class handles interactions with the Firebase Firestore 'FriendList' and 'friendRequests'
 * collections for the QuizBattle application. It includes methods for fetching, updating, adding, and removing
 * friend lists, friend requests, and friends.
 */
class FirestoreRepoFriend {
    private val db = FirebaseFirestore.getInstance()
    private val friendListCollection = db.collection("FriendList")
    private val friendRequestCollection = db.collection("friendRequests")
    private val fireStoreRepoUser = FirestoreRepoUser()
    private val friendRequestsList = mutableListOf<FriendRequest>()

    suspend fun getFriendList(uid: String): FriendList? {
        val friendListSnapshot = friendListCollection.document(uid).get().await()
        return if (friendListSnapshot.exists()) {
            friendListSnapshot.toObject(FriendList::class.java)
        } else {
            null
        }
    }

    suspend fun removeFriend(uid: String, friend: Player?) {
        // Remove friend from user's friend list
        val friendListRef = friendListCollection.document(uid)
        friendListRef.update("friendsList", FieldValue.arrayRemove(friend))

        // Remove user from friend's friend list
        val friendId = friend?.userUid
        if (friendId != null) {
            val friendRef = friendListCollection.document(friendId)
            friendRef.update("friendsList", FieldValue.arrayRemove(fireStoreRepoUser.getAsPlayer(uid)))
        }
    }

    suspend fun updateFriendList(uid: String, friendList: FriendList) {
        friendListCollection.document(uid).set(friendList).await()
    }

    suspend fun getFriendRequests(uid: String): List<FriendRequest>? {
        val querySnapshot = friendRequestCollection
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
        val querySnapshot = friendRequestCollection
            .get()
            .await()
        return if (!querySnapshot.isEmpty) {
            querySnapshot.toObjects(FriendRequest::class.java)
        } else {
            null
        }
    }

    suspend fun removeFriendRequest(friendRequest: FriendRequest) {
        friendRequestCollection
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

    suspend fun sendFriendRequest(senderId: String, receiverId: String) {
        val friendRequest = FriendRequest(senderId, receiverId)
        friendRequestCollection.add(friendRequest).await()
    }

    suspend fun addFriend(friendRequest: FriendRequest) {
        val receiverId = friendRequest.receiverId
        val senderId = friendRequest.senderId
        val receiver = fireStoreRepoUser.getAsPlayer(receiverId)
        val sender = fireStoreRepoUser.getAsPlayer(senderId)

        if (receiver != null && sender != null) {

            val friendListSender = getFriendList(receiverId)
            val friendListReceiver = getFriendList(senderId)

            if (friendListSender != null) {
                if (!friendListSender.friendsList.contains(sender)) {
                    friendListSender.friendsList.add(sender)
                    updateFriendList(receiverId, friendListSender)
                }
            } else {
                val newFriendList = FriendList(receiverId, mutableListOf(sender))
                updateFriendList(receiverId, newFriendList)
            }

            if (friendListReceiver != null) {
                if (!friendListReceiver.friendsList.contains(receiver)) {
                    friendListReceiver.friendsList.add(receiver)
                    updateFriendList(senderId, friendListReceiver)
                }
            } else {
                val newFriendList = FriendList(senderId, mutableListOf(receiver))
                updateFriendList(senderId, newFriendList)
            }
            removeFriendRequest(friendRequest)
        }
    }

    suspend fun getFriendRequests(onComplete: (List<FriendRequest>) -> Unit, userId:String) {
        getFriendRequests(userId)?.let {
            friendRequestsList.clear()
            friendRequestsList.addAll(it)
            onComplete(friendRequestsList)
        }
    }

    suspend fun removeFriendRequest(friendRequest: FriendRequest, onComplete: () -> Unit) {
        removeFriendRequest(friendRequest)
        friendRequestsList.remove(friendRequest)
        onComplete()
    }
}