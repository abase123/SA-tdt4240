package com.example.QuizBattle.model.FriendModel

data class FriendRequest(
    val senderId: String,
    val receiverId: String
){
    constructor() : this("", "")
}
