package com.example.QuizBattle.model.FriendModel
/**
 * The FriendRequest data class represents a friend request within the QuizBattle application.
 * It stores the unique user IDs of the sender and the receiver of the friend request.
 *
 * This class includes an empty constructor for Firestore deserialization and a primary
 * constructor for general use.
 */
data class FriendRequest(
    val senderId: String,
    val receiverId: String
){
    constructor() : this("", "")
}
