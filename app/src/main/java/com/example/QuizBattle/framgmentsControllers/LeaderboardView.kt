package com.example.QuizBattle.framgmentsControllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.QuizBattle.R
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoLeaderboard
import com.example.QuizBattle.framgmentsControllers.adapters.LeaderboardAdapter
import kotlinx.coroutines.launch

class LeaderboardView : Fragment() {
    private val fireStoreRepoLeaderBoard = FirestoreRepoLeaderboard()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        // Initialize your custom adapter with an empty list, as we'll update it with data later
        val leaderboardAdapter = LeaderboardAdapter(emptyList())

        recyclerView.adapter = leaderboardAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Retrieve the leaderboard data from Firestore and update the adapter
        lifecycleScope.launch {
            val topPlayers = fireStoreRepoLeaderBoard.getTopPlayers()
            leaderboardAdapter.updateData(topPlayers)
        }
    }

}