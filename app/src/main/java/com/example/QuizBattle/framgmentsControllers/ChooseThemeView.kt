package com.example.QuizBattle.framgmentsControllers

import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.controller.ViewChangeListener

class ChooseThemeView : Fragment(){
    private lateinit var descriptionTextView: TextView
    private lateinit var startQuizButton: Button
    private lateinit var goBackButton: Button
    private lateinit var loadingProgressBar: ProgressBar
    private var viewChangeListener: ViewChangeListener? = null
    private lateinit var themeImage: ImageView
    private lateinit var diffTextView: TextView
    private lateinit var gameController: GameController
}