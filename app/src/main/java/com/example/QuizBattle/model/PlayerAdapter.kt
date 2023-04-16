package com.example.QuizBattle.model

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.QuizBattle.R
import com.example.QuizBattle.model.Player
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.QuizBattle.databinding.FragmentSearchFriendsBinding

class PlayerAdapter : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    private val players: MutableList<Player> = mutableListOf()
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun setPlayers(players: List<Player>) {
        this.players.clear()
        this.players.addAll(players)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        holder.bind(player)

        holder.btnSendRequest.setOnClickListener {
            db.collection("Users")
                .whereEqualTo("displayName", player.displayName)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        for (document in querySnapshot.documents) {
                            val receiverId = document.id
                            val friendRequest = FriendRequest(senderId = auth.currentUser!!.uid, receiverId = receiverId)
                            db.collection("friendRequests")
                                .add(friendRequest)
                                .addOnSuccessListener {
                                    holder.btnSendRequest.text = "Request Sent"
                                    holder.btnSendRequest.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.btn_disabled))

                                    holder.btnSendRequest.isEnabled = false
                                }
                                .addOnFailureListener { e ->
                                    val binding = FragmentSearchFriendsBinding.inflate(LayoutInflater.from(holder.itemView.context))
                                    val recyclerView = binding.recyclerView
                                    val snackbar = Snackbar.make(recyclerView, "Error sending friend request", Snackbar.LENGTH_SHORT)
                                    snackbar.show()
                                }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("FirestoreError", "Error querying for user with the given display name", e)
                }
        }

    }

    override fun getItemCount(): Int {
        return players.size
    }

    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvDisplayName: TextView = itemView.findViewById(R.id.tvDisplayName)
        private val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        val btnSendRequest: Button = itemView.findViewById(R.id.btnSendRequest)


        fun bind(player: Player) {
            tvDisplayName.text = player.displayName
            tvEmail.text = player.email
        }
    }
}
