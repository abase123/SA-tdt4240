package com.example.QuizBattle.views.FriendsViews
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.QuizBattle.R
import com.example.QuizBattle.databinding.FragmentSearchFriendsBinding
import com.example.QuizBattle.model.FirestoreRepoes.FireStoreRepoUser
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
    val fireStoreRepoUser = FireStoreRepoUser()

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

        val player =auth.currentUser
        if(player!=null) {
            coroutineScope.launch {
                currentUser = fireStoreRepoUser.getAsPlayer(player.uid)

            }
        }


        return binding.root
    }

    private fun searchPlayers(query: String) {
        // Query Firestore to search for players with matching display names
        firestore.collection("Users")
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
                        if (player.userEmail != auth.currentUser?.email) {
                            val friends= currentUser?.friends
                            if(friends==null) {
                                players.add(player)
                            }else {
                                if(!friends.contains(player)) {
                                    players.add(player)
                                }
                            }
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
