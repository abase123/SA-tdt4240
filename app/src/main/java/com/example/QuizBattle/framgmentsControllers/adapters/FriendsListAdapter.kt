package com.example.QuizBattle.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.QuizBattle.R
import com.example.QuizBattle.model.PlayerModel.Player
import android.util.Log
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoUser
import com.example.QuizBattle.model.FriendModel.FriendList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FriendsListAdapter() : RecyclerView.Adapter<FriendsListAdapter.FriendViewHolder>() {

    private var friendsList: MutableList<Player> = mutableListOf()
    private val auth = FirebaseAuth.getInstance()
    private val playerId: String = auth.currentUser?.uid ?: String()
    private val db = FirebaseFirestore.getInstance()
    private val fireStoreRepoUser = FirestoreRepoUser()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var currentPlayer:Player?=null
    inner class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val removeButton = itemView.findViewById<TextView>(R.id.removeFriendButton)

        fun bind(friend: Player?) {
            removeButton.setOnClickListener {
                //remove locally
                val position = friendsList.indexOf(friend)
                if (position != -1) {
                    friendsList.removeAt(position)
                }
                //remove from firestore
                val friendListRef = db.collection("FriendList").document(playerId)
                friendListRef.update("friendsList", friendsList)

                // Remove current player from friend's list
                val friendId = friend?.userUid
                if (friendId != null) {
                    val friendRef = db.collection("FriendList").document(friendId)
                    friendRef.get().addOnSuccessListener { documentSnapshot ->
                        val friendList = documentSnapshot.toObject<FriendList>()
                        if (friendList != null) {
                            val flist = friendList.friendsList
                            flist.remove(currentPlayer)
                            //friendRef.set(friendList)
                            friendRef.update("friendsList", flist)
                        }
                    }
                    notifyDataSetChanged()
                }
            }
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_card, parent, false)

        return FriendViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        coroutineScope.launch {
            currentPlayer=fireStoreRepoUser.getAsPlayer(playerId)
        }
        val friend = friendsList.getOrNull(position)
        holder.bind(friend)

        holder.itemView.findViewById<TextView>(R.id.usernameUsercard).text = friend?.displayName
        //notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return friendsList.size
    }

    fun setFriendsList(friendsList: MutableList<Player>) {
        this.friendsList = friendsList
        notifyDataSetChanged()
    }

    fun clearFriendsList() {
        this.friendsList = mutableListOf()
        notifyDataSetChanged()
    }

}