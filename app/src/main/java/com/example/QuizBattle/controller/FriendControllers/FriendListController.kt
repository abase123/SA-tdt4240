package com.example.QuizBattle.controller.FriendControllers

import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoFriend
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoUser
import com.example.QuizBattle.model.PlayerModel.Player
import com.google.firebase.auth.FirebaseAuth
/**
 * The FriendListController class is responsible for managing the friends list in the QuizBattle application.
 * It handles retrieving the current player, removing friends, and getting the friends list.
 */
class FriendListController() {
    private val fireStoreRepoUser = FirestoreRepoUser()
    private val firestoreRepoFriend = FirestoreRepoFriend()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    suspend fun getCurrentPlayer(): Player? {
        return fireStoreRepoUser.getAsPlayer(auth.currentUser!!.uid)
    }

    suspend fun removeFriend(friend: Player?) {
        val playerId = auth.currentUser?.uid

        if (playerId != null && friend != null) {
            firestoreRepoFriend.removeFriend(playerId, friend)
        }
    }


    suspend fun getFriendsList(playerId: String): MutableList<Player> {
        return firestoreRepoFriend.getFriendList(playerId)?.friendsList ?: mutableListOf()
    }


}

