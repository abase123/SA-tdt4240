package com.example.QuizBattle.viewControllers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.QuizBattle.R
import com.example.QuizBattle.model.PlayerModel.Player
import com.example.QuizBattle.controller.FriendControllers.FriendListController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FriendsListAdapter(private val friendListController: FriendListController) : RecyclerView.Adapter<FriendsListAdapter.FriendViewHolder>() {
    private var friendsList: MutableList<Player> = mutableListOf()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    inner class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val removeButton = itemView.findViewById<TextView>(R.id.removeFriendButton)

        fun bind(friend: Player?) {
            removeButton.setOnClickListener {
                // remove locally
                val position = friendsList.indexOf(friend)
                if (position != -1) {
                    friendsList.removeAt(position)
                }
                coroutineScope.launch {
                friendListController.removeFriend(friend)
                }
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_card, parent, false)

        return FriendViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {

        val friend = friendsList.getOrNull(position)
        holder.bind(friend)

        holder.itemView.findViewById<TextView>(R.id.usernameUsercard).text = friend?.displayName
        holder.itemView.findViewById<TextView>(R.id.userScore).text= "AllTimeScore: " + friend?.allTimeScore.toString()

    }

    override fun getItemCount(): Int {
        return friendsList.size
    }

    fun setFriendsList(friendsList: MutableList<Player>) {
        this.friendsList = friendsList
        notifyDataSetChanged()
    }
}