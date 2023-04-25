package com.example.QuizBattle.model.FriendModel


import com.example.QuizBattle.model.PlayerModel.Player
/**
 * The FriendList data class represents a list of friends for a specific player within the
 * QuizBattle application. It stores the unique user ID of the player and a mutable list of
 * their friends as Player objects.
 *
 * This class includes an empty constructor for Firestore deserialization and a primary
 * constructor for general use.
 */
data class FriendList (
    val playerId: String,
    val friendsList: MutableList<Player>
){
    constructor() : this("", mutableListOf())
}