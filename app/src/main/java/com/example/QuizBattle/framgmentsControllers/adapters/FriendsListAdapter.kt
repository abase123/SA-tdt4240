package com.example.QuizBattle.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.QuizBattle.R
import com.example.QuizBattle.model.PlayerModel.Player

class FriendsListAdapter(private val friendsList: MutableList<Player>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_FRIEND = 1
    }

    inner class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFriendName = itemView.findViewById<TextView>(R.id.usernameUsercard)
        val removeButton = itemView.findViewById<TextView>(R.id.removeFriendButton)

        init {
            removeButton.setOnClickListener {
                friendsList?.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }

    }
    inner class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEmptyFriendsListMessage = itemView.findViewById<TextView>(R.id.tvEmptyFriendsListMessage)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_EMPTY -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_empty_friends_list, parent, false)
                EmptyViewHolder(itemView)
            }
            else -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_card, parent, false)
                FriendViewHolder(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FriendViewHolder) {
            if (friendsList?.size  != 0){
                val friend = friendsList?.get(position)
                holder.tvFriendName.text = friend?.displayName
            }
        } else if (holder is EmptyViewHolder) {
            holder.tvEmptyFriendsListMessage.text = "No friends added yet."
        }
    }

    override fun getItemCount(): Int {
        return if (friendsList.isNullOrEmpty()) {
            1
        } else {
            friendsList.size
        }
    }
}
