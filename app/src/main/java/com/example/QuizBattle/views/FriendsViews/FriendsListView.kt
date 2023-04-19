package com.example.QuizBattle.views.FriendsViews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.QuizBattle.R
import com.example.QuizBattle.model.FirestoreRepoes.FireStoreRepoUser
import com.example.QuizBattle.model.FriendModel.FriendList
import com.example.QuizBattle.model.PlayerModel.Player
import com.example.QuizBattle.views.DividerItemDecorator
import com.example.QuizBattle.views.adapters.FriendsListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class FriendsListView : Fragment(){
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val fireStoreRepoUser = FireStoreRepoUser()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var friendsListAdapter: FriendsListAdapter
    val currentUser =auth.currentUser


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_friends_list, container, false)

        val rvFriendsList = view.findViewById<RecyclerView>(R.id.rvFriendsList)
        rvFriendsList.layoutManager = LinearLayoutManager(context)

        val dividerWidthPx = resources.getDimensionPixelSize(R.dimen.divider_width)
        rvFriendsList.addItemDecoration(DividerItemDecorator(dividerWidthPx))
        rvFriendsList.isNestedScrollingEnabled = false

        if(currentUser != null) {
            friendsListAdapter = FriendsListAdapter()
            rvFriendsList.adapter = friendsListAdapter
        }
        loadFriendsList()

        return view
    }

    private fun loadFriendsList() {
        if(currentUser != null) {
            coroutineScope.launch {
                val player = fireStoreRepoUser.getAsPlayer(currentUser.uid)
                if(player != null) {
                    val friendListRef = db.collection("FriendList").document(currentUser.uid)
                    friendListRef.get().addOnSuccessListener { documentSnapshot ->
                        val friendList = documentSnapshot.toObject<FriendList>(FriendList::class.java)
                        if(friendList != null && friendList.friendsList.isNotEmpty()) {
                            friendsListAdapter.setFriendsList(friendList.friendsList)
                            friendsListAdapter.notifyDataSetChanged()
                        } else {
                            friendsListAdapter.clearFriendsList()
                        }
                    }
                }
            }
        }
    }
}
