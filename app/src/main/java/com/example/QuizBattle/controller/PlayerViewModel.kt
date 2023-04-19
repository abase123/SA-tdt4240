package com.example.QuizBattle.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoUser
import com.example.QuizBattle.model.PlayerModel.Player
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

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
        val updatesMap = mapOf(
            "score" to player.allTimeScore,
            "dailyQuizTaken" to player.dailyQuizTaken,
            "numQuizzesTaken" to player.numQuizzesTaken
        )
        viewModelScope.launch {
            fireStoreRepoUser.updatePlayerData(player.userUid, updatesMap)
        }
    }

}