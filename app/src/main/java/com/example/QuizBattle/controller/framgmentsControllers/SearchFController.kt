package com.example.QuizBattle.controller.framgmentsControllers

import android.util.Log
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.example.QuizBattle.R
import com.example.QuizBattle.databinding.FragmentSearchFriendsBinding
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoUser
import com.example.QuizBattle.model.FriendModel.FriendList
import com.example.QuizBattle.model.FriendModel.FriendRequest
import com.example.QuizBattle.model.PlayerModel.Player
import com.example.QuizBattle.view.adapters.SearchFriendsAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchFController (private val adapter: SearchFriendsAdapter){
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private var firestoreRepoUser = FirestoreRepoUser()
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
                    // Query Firestore to search for players with matching display names
                    val players = firestoreRepoUser.searchUsersByName(query)
                    getFriendList(players, currentUser!!)
                }
            }
        }
    }

    private fun getFriendList(players: MutableList<Player>, currentUser: Player) {
        val userId = currentUser.userUid
        coroutineScope.launch {
            // Get friend list for current user
            firestoreRepoUser.getFriendList(userId)?.let { friendList ->
                val friendEmails = friendList.friendsList.map { it.userEmail }

                // Add players who are not already friends
                val playersList =
                    players.filter { it.userEmail != currentUser.userEmail && it.userEmail !in friendEmails }
                adapter.setPlayers(playersList.toMutableList())
                searchCallback?.onSearchComplete()
            }
        }
    }



    fun sendRequest(player:Player, holder: SearchFriendsAdapter.PlayerViewHolder) {
        db.collection("Users")
            .whereEqualTo("displayName", player.displayName)
            .get()
            .addOnSuccessListener {querySnapshot ->
                checkFriendRequest(querySnapshot, holder)
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error querying for user with the given display name", e)
            }
    }

    private fun checkFriendRequest(querySnapshot: QuerySnapshot, holder: SearchFriendsAdapter.PlayerViewHolder) {
        if (!querySnapshot.isEmpty) {
            for (document in querySnapshot.documents) {
                val receiverId = document.id
                coroutineScope.launch {
                    val friendRequests = firestoreRepoUser.getFriendRequests(receiverId)
                    if (friendRequests == null || friendRequests.none { it.senderId == auth.currentUser!!.uid }) {
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
                firestoreRepoUser.sendFriendRequest(auth.currentUser!!.uid, receiverId)
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