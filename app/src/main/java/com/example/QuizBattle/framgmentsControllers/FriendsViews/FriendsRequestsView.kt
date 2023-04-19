package com.example.QuizBattle.views.FriendsViews

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoUser
import com.example.QuizBattle.model.FriendModel.FriendList
import com.example.QuizBattle.model.FriendModel.FriendRequest
import com.example.QuizBattle.model.PlayerModel.Player
import com.example.QuizBattle.views.adapters.FriendRequestsAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FriendsRequestsView : Fragment() {
    private val auth = FirebaseAuth.getInstance()
    private lateinit var rvFriendRequests: RecyclerView
    private val friendRequestsList = mutableListOf<FriendRequest>()
    private val fireStoreRepoUser = FirestoreRepoUser()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val db = Firebase.firestore



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_friends_requests, container, false)

        rvFriendRequests = view.findViewById<RecyclerView>(R.id.rvFriendRequests)
        rvFriendRequests.layoutManager = LinearLayoutManager(context)

        val listener = object : FriendRequestsAdapter.FriendRequestClickListener {
            override fun onAcceptClick(friendRequest: FriendRequest) {
                coroutineScope.launch {
                    val receiver= fireStoreRepoUser.getAsPlayer(friendRequest.receiverId)
                    val sender=fireStoreRepoUser.getAsPlayer(friendRequest.senderId)
                    if(receiver!=null && sender!=null){
                        val receiverId = friendRequest.receiverId
                        val senderId = friendRequest.senderId
                        val friendListSender= db.collection("FriendList").document(receiverId)
                        val friendListReceiver = db.collection("FriendList").document(senderId)

                        friendListSender.get()
                            .addOnSuccessListener { documentSnapshot ->
                                if (documentSnapshot.exists()) {
                                    val friendList = documentSnapshot.toObject(FriendList::class.java)?: FriendList()
                                    if(!friendList.friendsList.contains(sender)) {
                                        friendList?.friendsList?.add(sender)
                                        friendListSender.set(friendList)
                                    }
                                }
                                else {
                                    val friendList = FriendList(receiverId, mutableListOf(sender))
                                    db.collection("FriendList")
                                        .document(receiverId)
                                        .set(friendList)
                                }
                        }

                        friendListReceiver.get()
                            .addOnSuccessListener { documentSnapshot ->
                                if (documentSnapshot.exists()) {
                                    val friendList = documentSnapshot.toObject(FriendList::class.java) ?: FriendList()
                                    if (!friendList.friendsList.contains(receiver)) {
                                        friendList.friendsList.add(receiver)
                                        friendListReceiver.set(friendList)
                                    }
                                } else {
                                    val friendList = FriendList(senderId, mutableListOf(receiver))
                                    db.collection("FriendList")
                                        .document(senderId)
                                        .set(friendList)
                                }
                            }
                    }
                }
                removeFriendRequest(friendRequest)

            }

            override fun onRejectClick(friendRequest: FriendRequest) {
                removeFriendRequest(friendRequest)
            }
        }
        val friendRequestsAdapter = FriendRequestsAdapter(friendRequestsList, listener)

        rvFriendRequests.adapter = friendRequestsAdapter

        getFriendRequests()

        return view
    }

    private fun getFriendRequests(): List<FriendRequest> {
        val db = FirebaseFirestore.getInstance()
        val currentUser=auth.currentUser
        if(currentUser!=null) {
            val currentPlayerId = currentUser.uid
            db.collection("friendRequests")
                .whereEqualTo("receiverId", currentPlayerId)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    friendRequestsList.clear()
                    for (document in querySnapshot.documents) {
                        val friendRequest = document.toObject(FriendRequest::class.java)
                        friendRequest?.let {
                            friendRequestsList.add(it)
                        }
                    }
                    rvFriendRequests.adapter?.notifyDataSetChanged()

                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, "Error getting friend requests: ", exception)
                }

        }
        return friendRequestsList
    }


    private fun removeFriendRequest(friendRequest: FriendRequest) {
        val db = FirebaseFirestore.getInstance()

        db.collection("friendRequests")
            .whereEqualTo("receiverId", friendRequest.receiverId)
            .whereEqualTo("senderId", friendRequest.senderId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    document.reference.delete()
                        .addOnSuccessListener {
                            friendRequestsList.remove(friendRequest)
                            rvFriendRequests.adapter?.notifyDataSetChanged()
                        }
                        .addOnFailureListener { exception ->
                            Log.e(TAG, "Error deleting friend request: ", exception)
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting friend requests: ", exception)
            }
    }
}