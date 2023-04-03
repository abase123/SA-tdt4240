package com.example.QuizBattle.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.QuizBattle.R
import com.google.android.gms.common.util.concurrent.HandlerExecutor

class LoadingQuiz: Fragment(){
    private lateinit var descriptionTextView: TextView
    private lateinit var startQuizButton: Button
    private lateinit var loadingProgressBar: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.loading_quiz, container, false)
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        descriptionTextView=view.findViewById(R.id.descriptionTextView)
        startQuizButton=view.findViewById(R.id.startQuizButton)
        loadingProgressBar=view.findViewById(R.id.loadingProgressBar)

        descriptionTextView.text="Loading Today's quiz ..."

        startQuizButton.visibility=View.GONE
        loadingProgressBar.visibility=View.VISIBLE
    }

    fun onQuizLoaded(){
        startQuizButton.visibility=View.VISIBLE
        loadingProgressBar.visibility=View.GONE


    }


}