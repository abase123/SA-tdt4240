package com.example.QuizBattle.controller

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoUser
import com.example.QuizBattle.model.PlayerModel.Player
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

/**

PlayerViewModel is a class that serves as a ViewModel for managing Player data.
It utilizes the FirestoreRepoUser repository to fetch and update player data in the Firestore database.
The class provides LiveData to observe the current player and includes functions to load and update player data.
*/
class PlayerViewModel: ViewModel() {
    private val fireStoreRepoUser=FirestoreRepoUser()
    private val _player= MutableLiveData<Player?>()
    val player:LiveData<Player?> get() = _player
    fun loadPlayerData(){
        val currentUser=FirebaseAuth.getInstance().currentUser
        if(currentUser!=null){
            val uid= currentUser.uid
            viewModelScope.launch { _player.value = fireStoreRepoUser.getAsPlayer(uid) }
        }
    }
    fun updatePlayerDataInFirestore(player: Player){
        Log.d("PlayerViewModel", "updatePlayerDataInFirestore called")
        val updatesMap = mapOf(
            "allTimeScore" to player.allTimeScore,
            "dailyQuizTaken" to player.dailyQuizTaken,
            "numQuizzesTaken" to player.numQuizzesTaken
        )
        viewModelScope.launch {
            fireStoreRepoUser.updatePlayerData(player.userUid, updatesMap)
        }
    }

}