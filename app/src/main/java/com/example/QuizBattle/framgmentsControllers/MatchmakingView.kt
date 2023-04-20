package com.example.QuizBattle.framgmentsControllers

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.controller.UserInputEvent
import com.example.QuizBattle.controller.ViewChangeListener

class MatchmakingView: Fragment() {
    private lateinit var matchmakingTextView: TextView
    private lateinit var readyBattleButton: Button
    private lateinit var cancelBtn: Button
    private lateinit var matchmakingProgressBar: ProgressBar
    private var viewChangeListener: ViewChangeListener? = null
    private lateinit var waitingTextView: TextView
    private lateinit var gameController: GameController
    private lateinit var matchCountdown: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matchmaking, container, false)
    }

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

    fun onCancelClicked() {
        gameController.gameEngine.
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)

        cancelBtn.setOnClickListener {
            onCancelClicked()
            viewChangeListener?.onUserInput(UserInputEvent.RETURN_HOME)
        }

        gameController = activity as GameController
        gameController.gameEngine.fragmentLoadingState.setLoading(false)
    }

    @SuppressLint("SetTextI18n")
    private fun setup(view: View) {
        matchmakingTextView = view.findViewById(R.id.matchmakingTextView)
        readyBattleButton = view.findViewById(R.id.startBattleButton)
        matchmakingProgressBar = view.findViewById(R.id.matchmakingProgressBar)
        cancelBtn = view.findViewById(R.id.cancelBtn)
        waitingTextView = view.findViewById(R.id.waitingTextView)

        matchmakingTextView.text = "Matchmaking in progress ..."
        readyBattleButton.visibility = View.INVISIBLE
        matchmakingProgressBar.visibility = View.VISIBLE


    }

    fun onOpponentFound(){
        matchmakingTextView.text = "Match will start in ..."
        cancelBtn.visibility = View.INVISIBLE
        matchmakingProgressBar.visibility = View.INVISIBLE

        var count = 5
        val timer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilStart: Long) {
                matchCountdown.text = count.toString()
                count --
            }

            override fun onFinish() {
                matchCountdown.text = "GO"
                //CHANGE SCENE
            }
        }


    }
}