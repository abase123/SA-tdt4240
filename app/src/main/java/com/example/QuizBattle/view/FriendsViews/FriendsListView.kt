package com.example.QuizBattle.view.FriendsViews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.FriendframgmentsControllers.FriendListController
import com.example.QuizBattle.view.adapters.FriendsListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FriendsListView : Fragment() {
private val auth = FirebaseAuth.getInstance()
private val db = FirebaseFirestore.getInstance()
private val friendListController = FriendListController(auth, db)
private val coroutineScope = CoroutineScope(Dispatchers.Main)
private lateinit var friendsListAdapter: FriendsListAdapter
    private lateinit var friendRequestsView: FriendsRequestsView

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

    if (auth.currentUser != null) {
        friendsListAdapter = FriendsListAdapter(friendListController)
        friendRequestsView = FriendsRequestsView()
        rvFriendsList.adapter = friendsListAdapter
        coroutineScope.launch {
            val player = friendListController.getCurrentPlayer()
            val friendsList = friendListController.getFriendsList(player!!.userUid)
            friendsListAdapter.setFriendsList(friendsList)
            friendsListAdapter.notifyDataSetChanged()
        }
    }

    return view
}

override fun onDestroy() {
    super.onDestroy()
        coroutineScope.cancel()
    }
}
