package com.example.QuizBattle.view.FriendsViews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.FriendControllers.RequestsController
import com.example.QuizBattle.model.FriendModel.FriendRequest
import com.example.QuizBattle.view.adapters.FriendRequestsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class FriendsRequestsView() : Fragment() {
    private lateinit var rvFriendRequests: RecyclerView
    private val friendRequestsList = mutableListOf<FriendRequest>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_friends_requests, container, false)

        rvFriendRequests = view.findViewById<RecyclerView>(R.id.rvFriendRequests)
        rvFriendRequests.layoutManager = LinearLayoutManager(context)

        val reqController = RequestsController(rvFriendRequests)
        val listener = object : FriendRequestsAdapter.FriendRequestClickListener {
            override fun onAcceptClick(friendRequest: FriendRequest) {
                    reqController.addFriend(friendRequest)
                    friendRequestsList.remove(friendRequest)
            }

            override fun onRejectClick(friendRequest: FriendRequest) {
                reqController.removeFriendRequest(friendRequest){
                    friendRequestsList.remove(friendRequest)
                }
            }
        }
        val friendRequestsAdapter = FriendRequestsAdapter(friendRequestsList, listener)
        rvFriendRequests.adapter = friendRequestsAdapter

        reqController.getFriendRequests { friendRequests ->
            friendRequestsList.clear()
            friendRequestsList.addAll(friendRequests)
            friendRequestsAdapter.notifyDataSetChanged()
        }


        return view
    }
}