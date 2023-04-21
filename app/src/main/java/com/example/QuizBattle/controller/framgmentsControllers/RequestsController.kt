package com.example.QuizBattle.controller.framgmentsControllers

import android.content.ContentValues
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoUser
import com.example.QuizBattle.model.FriendModel.FriendList
import com.example.QuizBattle.model.FriendModel.FriendRequest
import com.example.QuizBattle.model.PlayerModel.Player
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RequestsController(private val rvFriendRequests: RecyclerView){
    private val friendRequestsList = mutableListOf<FriendRequest>()
    private val fireStoreRepoUser = FirestoreRepoUser()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser


    fun addFriend(friendRequest: FriendRequest) {
        coroutineScope.launch {
            val receiverId = friendRequest.receiverId
            val senderId = friendRequest.senderId
            val receiver = fireStoreRepoUser.getAsPlayer(receiverId)
            val sender = fireStoreRepoUser.getAsPlayer(senderId)

            if (receiver != null && sender != null) {

                val friendListSender = fireStoreRepoUser.getFriendList(receiverId)
                val friendListReceiver = fireStoreRepoUser.getFriendList(senderId)

                if (friendListSender != null) {
                    if (!friendListSender.friendsList.contains(sender)) {
                        friendListSender.friendsList.add(sender)
                        fireStoreRepoUser.updateFriendList(receiverId, friendListSender)
                    }
                } else {
                    val newFriendList = FriendList(receiverId, mutableListOf(sender))
                    fireStoreRepoUser.updateFriendList(receiverId, newFriendList)
                }

                if (friendListReceiver != null) {
                    if (!friendListReceiver.friendsList.contains(receiver)) {
                        friendListReceiver.friendsList.add(receiver)
                        fireStoreRepoUser.updateFriendList(senderId, friendListReceiver)
                    }
                } else {
                    val newFriendList = FriendList(senderId, mutableListOf(receiver))
                    fireStoreRepoUser.updateFriendList(senderId, newFriendList)
                }

                rvFriendRequests.adapter?.notifyDataSetChanged()
            }
        }
    }

    fun getFriendRequests(onComplete: (List<FriendRequest>) -> Unit) {
        coroutineScope.launch {
            if(currentUser!=null){
                fireStoreRepoUser.getFriendRequests(currentUser.uid)?.let {
                    friendRequestsList.clear()
                    friendRequestsList.addAll(it)
                    onComplete(friendRequestsList)
                }
            }
        }
    }


    fun removeFriendRequest(friendRequest: FriendRequest, onComplete: () -> Unit) {
        coroutineScope.launch {
            fireStoreRepoUser.removeFriendRequest(friendRequest)
            friendRequestsList.remove(friendRequest)
            onComplete()
            rvFriendRequests.adapter?.notifyDataSetChanged()
        }
    }

}