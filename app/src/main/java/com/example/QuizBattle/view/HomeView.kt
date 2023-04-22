package com.example.QuizBattle.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameActivity
import com.example.QuizBattle.controller.UserInputEvent
import com.example.QuizBattle.controller.ViewChangeListener


class HomeView : Fragment() {
    private var viewChangeListener: ViewChangeListener? = null
    private lateinit var gameActivity: GameActivity
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
        val infoBtnPlay:ImageButton=view.findViewById<ImageButton>(R.id.info_Playground)
        val infoBtnDaily:ImageButton=view.findViewById<ImageButton>(R.id.info_daily)

        playFriendBtn.setOnClickListener {
            viewChangeListener?.onUserInput(UserInputEvent.SELECT_THEME)
        }

        playDailyBtn.setOnClickListener {
            viewChangeListener?.onUserInput(UserInputEvent.LOAD_DAILY_QUIZ)
        }

        infoBtnPlay.setOnClickListener {
            showPlayInfo()
        }
        infoBtnDaily.setOnClickListener {
            showDailyInfo()
        }

        gameActivity = activity as GameActivity
        gameActivity.gameEngine.fragmentLoadingState.setLoading(false)
    }


    private fun showDailyInfo() {
        val builder = AlertDialog.Builder(gameActivity)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_game_info_daily, null)
        val infoText = dialogView.findViewById<TextView>(R.id.info_text)
        infoText.text=getString(R.string.descDaily)
        builder.setView(dialogView)
            .setTitle(getString(R.string.game_rules_title))
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }




    private fun showPlayInfo() {
        val builder = AlertDialog.Builder(gameActivity)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_game_info_play, null)
        val infoText = dialogView.findViewById<TextView>(R.id.info_text)
        infoText.text=getString(R.string.descPlay)
        builder.setView(dialogView)
            .setTitle(getString(R.string.game_rules_title))
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

}


