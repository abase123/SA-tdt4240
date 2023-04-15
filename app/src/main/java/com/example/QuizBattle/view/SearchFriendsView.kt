package com.example.QuizBattle.view
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.QuizBattle.model.PlayerAdapter
import com.example.QuizBattle.databinding.FragmentSearchFriendsBinding
import com.example.QuizBattle.model.Player
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.json.JSONObject

class SearchFriendsView : Fragment() {

    private lateinit var binding: FragmentSearchFriendsBinding
    private lateinit var playerAdapter: PlayerAdapter
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: Player

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchFriendsBinding.inflate(inflater, container, false)
        firestore = Firebase.firestore
        auth = FirebaseAuth.getInstance()


        playerAdapter = PlayerAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = playerAdapter
        }

        binding.btnSearch.setOnClickListener {
            val searchText = binding.etSearch.text.toString().trim()
            if (searchText.isNotEmpty()) {
                searchPlayers(searchText)
            }
        }

        // Get current user from Firestore
        firestore.collection("players")
            .document(auth.currentUser?.uid ?: "")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                currentUser = documentSnapshot.toObject(Player::class.java)!!
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }

        return binding.root
    }

    private fun searchPlayers(query: String) {
        // Query Firestore to search for players with matching display names
        firestore.collection("players")
            .orderBy("displayName")
            .startAt(query)
            .endAt(query + "\uf8ff")
            .limit(50)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val players = mutableListOf<Player>()
                for (document in querySnapshot.documents) {
                    val player = document.toObject(Player::class.java)
                    player?.let { player ->
                        // Exclude current user from search results
                        if (player.email != auth.currentUser?.email && !currentUser.friends.contains(player)) {
                            players.add(player)
                        }
                    }
                }
                playerAdapter.setPlayers(players)
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }
}
