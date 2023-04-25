package com.example.QuizBattle.viewControllers.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.QuizBattle.R
import com.example.QuizBattle.model.PlayerModel.Player
/**
 * The LeaderboardAdapter class is a custom RecyclerView.Adapter used to display a list of players
 * within the QuizBattle application's leaderboard. It takes a list of Player objects as input and
 * manages their ranking based on the all-time score. The adapter handles the layout and data binding
 * for each player item, displaying the player's name, score, and rank.
 */
class LeaderboardAdapter(private var players: List<Player>) :
    RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerName: TextView = itemView.findViewById(R.id.playerName)
        val playerScore: TextView = itemView.findViewById(R.id.playerScore)
        val rankingNumber: TextView = itemView.findViewById(R.id.rankingNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.leaderboard_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = players[position]
        holder.playerName.text = player.displayName
        holder.playerScore.text = player.allTimeScore.toString()
        holder.rankingNumber.text = "${position + 1}."
    }

    override fun getItemCount(): Int {
        return players.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newPlayers: List<Player>) {
        players = newPlayers
        notifyDataSetChanged()
    }
}
