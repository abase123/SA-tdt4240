package com.example.QuizBattle.framgmentsControllers

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameActivity
import com.example.QuizBattle.controller.GameCoreController.UserInputEvent
import com.example.QuizBattle.controller.GameCoreController.EventListener

/**
 * Fragment for loading the playground quiz in the game application.
 *
 * This fragment handles loading the playground quiz in the game application, including displaying a loading screen and information about the quiz theme and difficulty level. It also provides buttons for starting the quiz or returning to the home screen. This class extends the Android `Fragment` class and is designed to be used with the Android framework.
 *
 * @constructor Creates a new instance of the `LoadingPlaygroundQuizView` fragment.
 */


class LoadingPlaygroundQuizView: Fragment(){
    private lateinit var descriptionTextView: TextView
    private lateinit var startQuizButton: Button
    private lateinit var goBackButton: Button
    private lateinit var loadingProgressBar: ProgressBar
    private var eventListener: EventListener? = null
    private lateinit var themeImage: ImageView
    private lateinit var diffTextView: TextView
    private lateinit var gameActivity: GameActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loading_playgroundquiz, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EventListener) {
            eventListener = context
        } else {
            throw RuntimeException("$context must implement UserInputListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        eventListener = null
    }

    @SuppressLint("SetTextI18n")
    private fun setup(view: View){
        descriptionTextView=view.findViewById(R.id.descriptionTextView)
        startQuizButton=view.findViewById(R.id.startQuizButton)
        loadingProgressBar=view.findViewById(R.id.loadingProgressBar)
        goBackButton=view.findViewById(R.id.goBackBtn)
        themeImage=view.findViewById(R.id.imageView2)
        diffTextView=view.findViewById(R.id.diffText2)

        descriptionTextView.text="Loading The Quiz ..."
        startQuizButton.visibility=View.INVISIBLE
        loadingProgressBar.visibility=View.VISIBLE
        diffTextView.visibility = View.INVISIBLE

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)

        goBackButton.setOnClickListener {
            eventListener?.onUserInput(UserInputEvent.RETURN_HOME)
        }

        gameActivity = activity as GameActivity
        gameActivity.gameEngine.fragmentLoadingState.setLoading(false)


    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    fun onQuizLoaded(theme:String,quizDiff:String){
        startQuizButton.visibility=View.VISIBLE
        loadingProgressBar.visibility=View.GONE
        setTheme(theme)
        setDiff(quizDiff)
        startQuizButton.setOnClickListener {
            eventListener?.onUserInput(UserInputEvent.PLAY_PLAYGROUND)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setTheme(theme:String){
        descriptionTextView.text= "The theme of this Quiz: \n $theme"
        when (theme) {
            "Geography" -> themeImage.setImageResource(R.drawable.geo)
            "Sports" -> themeImage.setImageResource(R.drawable.sports)
            "History" -> themeImage.setImageResource(R.drawable.history)
            "General Knowledge" -> themeImage.setImageResource(R.drawable.gk)
        }

    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    private fun setDiff(quizDiff:String){
        diffTextView.text= "Difficulty: $quizDiff "
        when (quizDiff) {
            "Easy" -> diffTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.easy))
            "Medium" -> diffTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.medium))
            "Hard" -> diffTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.hard))
        }
        diffTextView.visibility=View.VISIBLE
    }

    fun onQuizNotAvailable(){
        AlertDialog.Builder(gameActivity)
            .setTitle("Quiz Not Available")
            .setMessage("The quiz is not available at the moment. ")
            .setPositiveButton("OK", null)
            .show()
    }



}