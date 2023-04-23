package com.example.QuizBattle.view

import com.example.QuizBattle.controller.gameStates.SelectTheme.OnThemeChangeListener
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameActivity
import com.example.QuizBattle.controller.GameCoreController.UserInputEvent
import com.example.QuizBattle.controller.GameCoreController.EventListener
/**

This class represents the fragment responsible for displaying the screen where the user can choose a theme for the quiz. It listens for user input to change the theme and load the quiz questions based on the selected theme. It also communicates with the GameActivity to indicate the loading state. The com.example.QuizBattle.controller.gameStates.SelectTheme.OnThemeChangeListener interface is implemented to notify other components of the application about the theme change.
 */

class ChooseThemeView : Fragment(){
    private var eventListener: EventListener? = null
    var themeChangeListener: OnThemeChangeListener? = null
    private lateinit var gameActivity: GameActivity
    private lateinit var sportsButton: RadioButton
    private lateinit var historyButton: RadioButton
    private lateinit var generalKnowledgeButton: RadioButton
    private lateinit var geographyButton: RadioButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chose_theme, container, false) as ViewGroup
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EventListener) {
            eventListener = context
        }
        else {
            throw RuntimeException("$context must implement UserInputListener")
        }
    }
    override fun onDetach() {
        super.onDetach()
        eventListener = null
    }

    private fun setup(view: View){
        sportsButton= view.findViewById(R.id.sport_theme)
        historyButton= view.findViewById(R.id.history_theme)
        geographyButton=view.findViewById((R.id.geo_theme))
        generalKnowledgeButton=view.findViewById((R.id.generalKnowledge_theme))
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
        gameActivity = activity as GameActivity
        gameActivity.gameEngine.fragmentLoadingState.setLoading(false)
        listenForThemes()

    }

    private fun listenForThemes(){
        sportsButton.setOnClickListener {
            themeChangeListener?.onThemeChanged("Sports")
            eventListener?.onUserInput(UserInputEvent.LOAD_PLAYGROUND_QUIZ)

        }

        historyButton.setOnClickListener {
            themeChangeListener?.onThemeChanged("History")
            eventListener?.onUserInput(UserInputEvent.LOAD_PLAYGROUND_QUIZ)

        }

        geographyButton.setOnClickListener {
            themeChangeListener?.onThemeChanged("Geography")
            eventListener?.onUserInput(UserInputEvent.LOAD_PLAYGROUND_QUIZ)
        }

        generalKnowledgeButton.setOnClickListener {
            themeChangeListener?.onThemeChanged("General Knowledge")
            eventListener?.onUserInput(UserInputEvent.LOAD_PLAYGROUND_QUIZ)
        }
    }
}




