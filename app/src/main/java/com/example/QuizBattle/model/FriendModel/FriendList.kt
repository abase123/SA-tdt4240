package com.example.QuizBattle.model.FriendModel


import com.example.QuizBattle.model.PlayerModel.Player

data class FriendList (
    val playerId: String,
    val friendsList: MutableList<Player>
){
    constructor() : this("", mutableListOf())
}