package com.example.QuizBattle.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.QuizBattle.R

class FriendsListView : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_friends_list, container, false)

        // Set up RecyclerView
        val rvFriendsList = view.findViewById<RecyclerView>(R.id.rvFriendsList)
        rvFriendsList.layoutManager = LinearLayoutManager(context) // Use LinearLayoutManager for linear list layout
        // Set up your friends list data, e.g., an ArrayList of Friend objects
        /*val friendsList = getFriendsList()
        // Create an adapter for the RecyclerView
        val friendsListAdapter = FriendsListAdapter(friendsList) //crear classe FriendsListAdapter
        rvFriendsList.adapter = friendsListAdapter*/

        return view
    }

    //convertir player a friend
    /*
    private fun getFriendsList(): ArrayList<Friend> {
        // Return a list of Friend objects representing the user's friends
        // Replace this with your actual implementation to fetch the friends data
        // from your data source, e.g., database, API, etc.
        val friendsList = ArrayList<Friend>()
        friendsList.add(Friend("Friend 1", "avatar1.png"))
        friendsList.add(Friend("Friend 2", "avatar2.png"))
        friendsList.add(Friend("Friend 3", "avatar3.png"))
        // Add more friends as needed
        return friendsList
    }
    data class Friend(val name: String, val avatar: String)*/
}