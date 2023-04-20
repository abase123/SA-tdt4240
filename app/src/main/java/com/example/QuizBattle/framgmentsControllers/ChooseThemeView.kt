package com.example.QuizBattle.framgmentsControllers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.controller.ViewChangeListener
import com.example.QuizBattle.controller.gameStates.PlayDailyQuizState.PlayDailyQuizViewModel

class ChooseThemeView : Fragment(){
    private lateinit var choseText: TextView
    private val themes = mutableListOf<RadioButton>()
    private lateinit var chosenTheme: RadioButton
    private var viewChangeListener: ViewChangeListener? = null
    private lateinit var gameController: GameController

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
        } else {
            throw RuntimeException("$context must implement UserInputListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        viewChangeListener = null
    }

    private fun showChosentTheme() {
        chosenTheme.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.chosen_theme))
    }
}