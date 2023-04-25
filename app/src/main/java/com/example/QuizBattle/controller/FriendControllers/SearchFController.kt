package com.example.QuizBattle.controller.FriendControllers

import android.util.Log
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.example.QuizBattle.R
import com.example.QuizBattle.databinding.FragmentSearchFriendsBinding
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoFriend
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoUser
import com.example.QuizBattle.model.PlayerModel.Player
import com.example.QuizBattle.viewControllers.adapters.SearchFriendsAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchFController (private val adapter: SearchFriendsAdapter){
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestoreRepoUser = FirestoreRepoUser()
    private val firestoreRepoFriend = FirestoreRepoFriend()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var currentUser:Player?=null
    private var searchCallback: SearchCallback? = null

    interface SearchCallback {
        fun onSearchComplete()
    }

    fun searchPlayers(query: String, callback: SearchCallback) {
        this.searchCallback = callback
        coroutineScope.launch {
            val user = auth.currentUser
            if (user != null) {
                currentUser = firestoreRepoUser.getAsPlayer(user.uid)
                if (currentUser != null) {
                    // Query Firestore to search for players with matching emails
                    val players = firestoreRepoUser.searchUsersByEmail(query)
                    getFriendList(players, currentUser!!)

                }
            }
        }
    }

    private fun getFriendList(players: MutableList<Player>, currentUser: Player) {
        val userId = currentUser.userUid
        coroutineScope.launch {
            // Get friend list for current user
            firestoreRepoFriend.getFriendList(userId)?.let { friendList ->
                val friendEmails = friendList.friendsList.map { it.userEmail }
                // Add players who are not already friends
                val playersList =
                    players.filter { it.userEmail != currentUser.userEmail && it.userEmail !in friendEmails }
                playersList.let { playersList ->
                    val allFriendRequests = firestoreRepoFriend.getAllFriendRequests()
                    if(allFriendRequests!=null) {
                        if(allFriendRequests.none {(it.senderId==playersList.get(0).userUid && it.receiverId == userId)
                                    || (it.senderId == userId && it.receiverId == playersList.get(0).userUid)}){
                            adapter.setPlayers(playersList.toMutableList())
                        }
                    }else {
                        adapter.setPlayers(playersList.toMutableList())
                    }

                }
                searchCallback?.onSearchComplete()
            }
        }
    }

    fun sendRequest(player:Player, holder: SearchFriendsAdapter.PlayerViewHolder) {
        coroutineScope.launch {
        firestoreRepoUser.getUserByName(player.displayName).addOnSuccessListener {querySnapshot ->
                checkFriendRequest(querySnapshot, holder)
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error querying for user with the given display name", e)
            }
        }
    }

    private fun checkFriendRequest(querySnapshot: QuerySnapshot, holder: SearchFriendsAdapter.PlayerViewHolder) {
        if (!querySnapshot.isEmpty) {
            for (document in querySnapshot.documents) {
                val receiverId = document.id
                coroutineScope.launch {
                    val currentUser = firestoreRepoUser.getAsPlayer(auth.currentUser?.uid!!)
                    val friendRequests = firestoreRepoFriend.getFriendRequests(receiverId)
                    if (friendRequests == null || friendRequests.none { it.senderId == currentUser?.userUid }) {
                        sendRequest(holder, receiverId)
                    }
                }
            }
        }
    }


    private fun disableButton(holder: SearchFriendsAdapter.PlayerViewHolder) {
        holder.btnSendRequest.text = "Request Sent"
        holder.btnSendRequest.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.btn_disabled))
        holder.btnSendRequest.isEnabled = false
    }

    private fun sendRequest(holder: SearchFriendsAdapter.PlayerViewHolder, receiverId:String){
        // Friend request does not exist, send request
        coroutineScope.launch {
            try {
                firestoreRepoFriend.sendFriendRequest(auth.currentUser!!.uid, receiverId)
                disableButton(holder)

            } catch (e: Exception) {
                val binding = FragmentSearchFriendsBinding.inflate(
                    LayoutInflater.from(holder.itemView.context)
                )
                val recyclerView = binding.recyclerView
                val snackbar = Snackbar.make(
                    recyclerView,
                    "Error sending friend request",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            }
        }
    }
}