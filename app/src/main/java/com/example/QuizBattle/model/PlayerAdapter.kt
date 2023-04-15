package com.example.QuizBattle.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.QuizBattle.R
import com.example.QuizBattle.model.Player

class PlayerAdapter : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    private val players: MutableList<Player> = mutableListOf()

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

        // Add click listener for "Send Request" button
        holder.btnSendRequest.setOnClickListener {
            // Perform action when "Send Request" button is clicked
            // You can implement the functionality to send friend request here
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
