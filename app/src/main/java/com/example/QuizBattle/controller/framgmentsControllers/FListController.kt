package com.example.QuizBattle.controller.framgmentsControllers

import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoUser
import com.example.QuizBattle.model.FriendModel.FriendList
import com.example.QuizBattle.model.PlayerModel.Player
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FListController(private val auth: FirebaseAuth, private val db: FirebaseFirestore) {
    private val fireStoreRepoUser = FirestoreRepoUser()

    suspend fun getCurrentPlayer(): Player? {
        return fireStoreRepoUser.getAsPlayer(auth.currentUser!!.uid)
    }

    suspend fun removeFriend(friend: Player?) {
        val playerId = auth.currentUser?.uid

        if (playerId != null && friend != null) {
            fireStoreRepoUser.removeFriend(playerId, friend)
        }
    }


    suspend fun getFriendsList(playerId: String): MutableList<Player> {
        return fireStoreRepoUser.getFriendList(playerId)?.friendsList ?: mutableListOf()
    }


}

