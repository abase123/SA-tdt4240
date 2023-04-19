package com.example.QuizBattle.views.FriendsViews
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.QuizBattle.R
import com.example.QuizBattle.databinding.FragmentSearchFriendsBinding
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoUser
import com.example.QuizBattle.model.FriendModel.FriendList
import com.example.QuizBattle.model.PlayerModel.Player
import com.example.QuizBattle.views.DividerItemDecorator
import com.example.QuizBattle.views.adapters.PlayerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchFriendsView : Fragment() {

    private lateinit var binding: FragmentSearchFriendsBinding
    private lateinit var playerAdapter: PlayerAdapter
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var currentUser: Player? = null
    val fireStoreRepoUser = FirestoreRepoUser()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

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

        val dividerWidthPx = resources.getDimensionPixelSize(R.dimen.divider_width)
        binding.recyclerView.addItemDecoration(DividerItemDecorator(dividerWidthPx))
        binding.recyclerView.isNestedScrollingEnabled = false

        binding.btnSearch.setOnClickListener {
            val searchText = binding.etSearch.text.toString().trim()
            if (searchText.isNotEmpty()) {
                searchPlayers(searchText)
            }
        }

        val player = auth.currentUser
        if (player != null) {
            coroutineScope.launch {
                currentUser = fireStoreRepoUser.getAsPlayer(player.uid)

            }
        }


        return binding.root
    }
    private fun searchPlayers(query: String) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Query Firestore to search for players with matching display names
            firestore.collection("Users")
                .orderBy("displayName")
                .startAt(query)
                .endAt(query + "\uf8ff")
                .limit(50)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val players = mutableListOf<Player>()
                    val userId = currentUser.uid

                    // Get friend list for current user
                    firestore.collection("FriendList")
                        .whereEqualTo("playerId", userId)
                        .get()
                        .addOnSuccessListener { friendListSnapshot ->

                            // Convert friend list to list of friend IDs
                            val friendEmails = friendListSnapshot.documents.firstOrNull()?.toObject(FriendList::class.java)?.friendsList?.map { it.userEmail} ?: emptyList()

                            // Add players who are not already friends
                            for (document in querySnapshot.documents) {
                                val player = document.toObject(Player::class.java)
                                player?.let { player ->
                                    if (player.userEmail != currentUser.email && player.userEmail !in friendEmails) {
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
                .addOnFailureListener { exception ->
                    exception.printStackTrace()
                }
        }
    }
}
