package com.example.QuizBattle.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.UserInputEvent
import com.example.QuizBattle.controller.UserInputListener
import com.google.type.Color

class LoadingQuizView: Fragment(){
    private lateinit var descriptionTextView: TextView
    private lateinit var startQuizButton: Button
    private lateinit var goBackButton: Button
    private lateinit var loadingProgressBar: ProgressBar
    private var userInputListener: UserInputListener? = null
    private lateinit var themeImage: ImageView
    private lateinit var diffTextView: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loading_quiz, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is UserInputListener) {
            userInputListener = context
        } else {
            throw RuntimeException("$context must implement UserInputListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        userInputListener = null
    }



    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        descriptionTextView=view.findViewById(R.id.descriptionTextView)
        startQuizButton=view.findViewById(R.id.startQuizButton)
        loadingProgressBar=view.findViewById(R.id.loadingProgressBar)
        goBackButton=view.findViewById(R.id.goBackBtn)
        themeImage=view.findViewById(R.id.imageView2)
        diffTextView=view.findViewById(R.id.diffText)
        descriptionTextView.text="Loading Today's quiz ..."

        goBackButton.setOnClickListener {
            userInputListener?.onUserInput(UserInputEvent.RETURN_HOME)
        }

        startQuizButton.visibility=View.INVISIBLE
        loadingProgressBar.visibility=View.VISIBLE
        diffTextView.visibility = View.INVISIBLE

    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    fun onQuizLoaded(theme:String,quizDiff:String){
        startQuizButton.visibility=View.VISIBLE
        loadingProgressBar.visibility=View.GONE
        descriptionTextView.text= "Today's  Theme : '$theme'"
        when (theme) {
            "Geography" -> themeImage.setImageResource(R.drawable.geo)
            "Sports" -> themeImage.setImageResource(R.drawable.sports)
            "History" -> themeImage.setImageResource(R.drawable.history)
            "General Knowledge" -> themeImage.setImageResource(R.drawable.gk)
        }
        startQuizButton.setOnClickListener {
            userInputListener?.onUserInput(UserInputEvent.PLAY_DAILYQUIZ)
        }


        when (quizDiff) {
            "Easy"   -> diffTextView.setTextColor(R.color.easy)
            "Medium" -> diffTextView.setTextColor(R.color.medium)
            "Hard"   -> diffTextView.setTextColor(R.color.hard)
        }
        diffTextView.text= "Difficulty: $quizDiff "
        diffTextView.visibility=View.VISIBLE



    }


}