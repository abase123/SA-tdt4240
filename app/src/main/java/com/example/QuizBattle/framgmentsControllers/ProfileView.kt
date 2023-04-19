package com.example.QuizBattle.framgmentsControllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.PlayerViewModel


class ProfileView : Fragment() {
    private lateinit var playerViewModel: PlayerViewModel
    private lateinit var userNameText:TextView
    private lateinit var userScoreText:TextView
    private lateinit var numQuizTakenText:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userNameText=view.findViewById(R.id.usernameUsercard)
        userScoreText=view.findViewById(R.id.allTimeScore)
        numQuizTakenText=view.findViewById(R.id.quizzesPlayed)

        playerViewModel = ViewModelProvider(requireActivity())[PlayerViewModel::class.java]

        playerViewModel.player.observe(viewLifecycleOwner) { player ->
            player?.let {
                userNameText.text = it.displayName
                userScoreText.text= it.allTimeScore.toString()
                numQuizTakenText.text=it.numQuizzesTaken.toString()
            }
        }

    }

}