package com.example.QuizBattle.framgmentsControllers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.controller.UserInputEvent
import com.example.QuizBattle.controller.ViewChangeListener



class HomeView : Fragment() {
    // TODO: Rename and change types of parameters
    private var viewChangeListener: ViewChangeListener? = null
    private lateinit var gameController: GameController


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ViewChangeListener) {
            viewChangeListener = context
        } else {
            throw RuntimeException("$context must implement UserInputListener")
        }
    }
    override fun onDetach() {
        super.onDetach()
        viewChangeListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val playFriendBtn=view.findViewById<Button>(R.id.playFriendButton)
        val playDailyBtn=view.findViewById<Button>(R.id.playdailyButton)

        playFriendBtn.setOnClickListener {
            viewChangeListener?.onUserInput(UserInputEvent.CHOOSE_CATEGORY)
        }

        playDailyBtn.setOnClickListener {
            viewChangeListener?.onUserInput(UserInputEvent.LOAD_DAILY_QUIZ)
        }
        gameController = activity as GameController
        gameController.gameEngine.fragmentLoadingState.setLoading(false)
    }


}