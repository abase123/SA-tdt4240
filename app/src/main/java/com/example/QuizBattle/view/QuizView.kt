package com.example.QuizBattle.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.UserInputEvent
import com.example.QuizBattle.controller.UserInputListener

class QuizView: Fragment() {

    private var userInputListener: UserInputListener? = null
    private lateinit var endQuizBtn:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
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
        endQuizBtn=view.findViewById(R.id.buttonEndQuiz)
        endQuizBtn.setOnClickListener {
            userInputListener?.onUserInput(UserInputEvent.RETURN_HOME)
        }


    }



}

