package com.example.QuizBattle.viewControllers.FriendsViews
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.QuizBattle.R
import com.example.QuizBattle.databinding.FragmentSearchFriendsBinding
import com.example.QuizBattle.controller.FriendControllers.SearchFController
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoUser
import com.example.QuizBattle.model.PlayerModel.Player
import com.example.QuizBattle.viewControllers.adapters.SearchFriendsAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
/**
 * The SearchFriendsView class is a Fragment used to search for friends within the QuizBattle
 * application. It provides a search bar for users to enter a search query and a RecyclerView
 * to display the search results. Users can send friend requests to other players from the
 * search results. This Fragment utilizes a SearchFriendsAdapter to display the search results
 * and a SearchFController to handle the search functionality.
 */
class SearchFriendsView : Fragment(), SearchFController.SearchCallback {

    private lateinit var binding: FragmentSearchFriendsBinding
    private lateinit var playerAdapter: SearchFriendsAdapter
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var currentUser: Player? = null
    private val fireStoreRepoUser = FirestoreRepoUser()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val searchFAdapter = SearchFriendsAdapter()
    private var searchFController = SearchFController(searchFAdapter)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchFriendsBinding.inflate(inflater, container, false)
        firestore = Firebase.firestore
        auth = FirebaseAuth.getInstance()


        playerAdapter = searchFAdapter
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = playerAdapter
        }

        val dividerWidthPx = resources.getDimensionPixelSize(R.dimen.divider_width)
        binding.recyclerView.addItemDecoration(DividerItemDecorator(dividerWidthPx))
        binding.recyclerView.isNestedScrollingEnabled = false

        searchFController = SearchFController(searchFAdapter)


        binding.btnSearch.setOnClickListener {
            val searchText = binding.etSearch.text.toString().trim()
            if (searchText.isNotEmpty()) {
                searchFController.searchPlayers(searchText, this)
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
    @SuppressLint("NotifyDataSetChanged")
    override fun onSearchComplete() {
        searchFAdapter.notifyDataSetChanged()
    }
}
