package com.example.QuizBattle.view

import OnThemeChangeListener
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.controller.UserInputEvent
import com.example.QuizBattle.controller.ViewChangeListener
import com.example.QuizBattle.view.adapters.LeaderboardAdapter
import com.google.api.ResourceDescriptor.History
import kotlinx.coroutines.launch


class ChooseThemeView : Fragment(){
    private var viewChangeListener: ViewChangeListener? = null
    var themeChangeListener: OnThemeChangeListener? = null
    private lateinit var gameController: GameController
    private lateinit var sportsButton: RadioButton
    private lateinit var historyButton: RadioButton
    private lateinit var generalKnowlegdeButton: RadioButton
    private lateinit var geographyButton: RadioButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chose_theme, container, false) as ViewGroup
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ViewChangeListener) {
            viewChangeListener = context
        }
        else {
            throw RuntimeException("$context must implement UserInputListener")
        }
    }
    override fun onDetach() {
        super.onDetach()
        viewChangeListener = null
    }

    private fun setup(view: View){
        sportsButton= view.findViewById(R.id.sport_theme)
        historyButton= view.findViewById(R.id.history_theme)
        geographyButton=view.findViewById((R.id.geo_theme))
        generalKnowlegdeButton=view.findViewById((R.id.generalKnowledge_theme))
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        setup(view)
        gameController = activity as GameController
        gameController.gameEngine.fragmentLoadingState.setLoading(false)
        listenForThemes()

    }

    private fun listenForThemes(){
        sportsButton.setOnClickListener {
            themeChangeListener?.onThemeChanged("Sports")
            viewChangeListener?.onUserInput(UserInputEvent.LOAD_PLAYGROUND_QUIZ)

        }

        historyButton.setOnClickListener {
            themeChangeListener?.onThemeChanged("History")
            viewChangeListener?.onUserInput(UserInputEvent.LOAD_PLAYGROUND_QUIZ)

        }

        geographyButton.setOnClickListener {
            themeChangeListener?.onThemeChanged("Geography")
            viewChangeListener?.onUserInput(UserInputEvent.LOAD_PLAYGROUND_QUIZ)
        }

        generalKnowlegdeButton.setOnClickListener {
            themeChangeListener?.onThemeChanged("General Knowledge")
            viewChangeListener?.onUserInput(UserInputEvent.LOAD_PLAYGROUND_QUIZ)
        }
    }
}




