package com.example.QuizBattle.controller.FriendControllers

import androidx.recyclerview.widget.RecyclerView
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoFriend
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoUser
import com.example.QuizBattle.model.FriendModel.FriendList
import com.example.QuizBattle.model.FriendModel.FriendRequest
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
/**
 * The RequestsController class is responsible for managing friend requests in the QuizBattle application.
 * It handles adding friends, getting friend requests, and removing friend requests.
 */
class RequestsController(private val rvFriendRequests: RecyclerView){

    private val firestoreRepoFriend = FirestoreRepoFriend()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser


    fun addFriend(friendRequest: FriendRequest) {
        coroutineScope.launch {
            firestoreRepoFriend.addFriend(friendRequest)
            rvFriendRequests.adapter?.notifyDataSetChanged()
        }
    }

    fun getFriendRequests(onComplete: (List<FriendRequest>) -> Unit) {
        coroutineScope.launch {
            if(currentUser!=null){
                firestoreRepoFriend.getFriendRequests(onComplete, currentUser.uid)
            }
        }
    }


    fun removeFriendRequest(friendRequest: FriendRequest, onComplete: () -> Unit) {
        coroutineScope.launch {
            firestoreRepoFriend.removeFriendRequest(friendRequest, onComplete)
            rvFriendRequests.adapter?.notifyDataSetChanged()
        }
    }
}