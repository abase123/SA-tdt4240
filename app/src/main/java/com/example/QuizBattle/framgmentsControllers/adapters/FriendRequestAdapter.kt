package com.example.QuizBattle.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.QuizBattle.model.FirestoreRepoes.FireStoreRepoUser
import com.example.QuizBattle.R
import com.example.QuizBattle.model.FriendModel.FriendRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FriendRequestsAdapter(private val friendRequests: List<FriendRequest>, private val listener: FriendRequestClickListener) :
    RecyclerView.Adapter<FriendRequestsAdapter.FriendRequestViewHolder>() {

    private val fireStoreRepoUser = FireStoreRepoUser()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    interface FriendRequestClickListener {
        fun onAcceptClick(friendRequest: FriendRequest)
        fun onRejectClick(friendRequest: FriendRequest)
    }

    inner class FriendRequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvFriendRequestName)
        private val btnAccept: Button = itemView.findViewById(R.id.btnAcceptRequest)
        private val btnReject: Button = itemView.findViewById(R.id.btnRejectRequest)

        suspend fun bind(friendRequest: FriendRequest) {
            coroutineScope.launch {
                val sender = fireStoreRepoUser.getAsPlayer(friendRequest.senderId)
                if (sender != null) {
                    tvName.text = sender.displayName
                }
            }
            btnAccept.setOnClickListener {
                listener.onAcceptClick(friendRequest)
            }

            btnReject.setOnClickListener {
                listener.onRejectClick(friendRequest)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendRequestViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_friend_request, parent, false)
        return FriendRequestViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FriendRequestViewHolder, position: Int) {
        val friendRequest = friendRequests[position]
        coroutineScope.launch {
            holder.bind(friendRequest)
        }
    }

    override fun getItemCount(): Int {
        return friendRequests.size
    }


}
