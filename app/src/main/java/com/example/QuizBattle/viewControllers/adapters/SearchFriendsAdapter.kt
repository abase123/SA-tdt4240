package com.example.QuizBattle.viewControllers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.QuizBattle.R
import com.example.QuizBattle.model.PlayerModel.Player
import com.example.QuizBattle.controller.FriendControllers.SearchFController
/**
 * The SearchFriendsAdapter class is a custom RecyclerView.Adapter used to display a list of players
 * within the QuizBattle application's search friends feature. It manages a list of Player objects and
 * allows users to send friend requests to other players. The adapter handles the layout and data binding
 * for each player item, displaying the player's name and a button to send a friend request.
 */
class SearchFriendsAdapter : RecyclerView.Adapter<SearchFriendsAdapter.PlayerViewHolder>() {

    private var players: MutableList<Player> = mutableListOf()
    private val searchFController = SearchFController(this)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        holder.bind(player)

        holder.btnSendRequest.setOnClickListener {
            searchFController.sendRequest(player, holder)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return players.size
    }

    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvDisplayName: TextView = itemView.findViewById(R.id.tvDisplayName)
        val btnSendRequest: Button = itemView.findViewById(R.id.btnSendRequest)
        fun bind(player: Player) {
            tvDisplayName.text = player.displayName
        }
    }

    fun setPlayers(players: MutableList<Player>) {
        this.players.clear()
        this.players.addAll(players)

    }
}