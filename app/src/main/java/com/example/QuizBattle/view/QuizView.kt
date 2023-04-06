package com.example.QuizBattle.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.UserInputEvent
import com.example.QuizBattle.controller.UserInputListener

class QuizView: Fragment() {

    private var userInputListener: UserInputListener? = null
    private lateinit var endQuizBtn:Button
    private lateinit var checkAnswer:Button
    private lateinit var option1: RadioButton
    private lateinit var option2: RadioButton
    private lateinit var option3: RadioButton
    private lateinit var option4: RadioButton
    private  lateinit var questionText: TextView
    private lateinit var timerText:TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is UserInputListener ) {
            userInputListener = context
        } else {
            throw RuntimeException("$context must implement UserInputListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        userInputListener = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        endQuizBtn=view.findViewById(R.id.buttonEndQuiz)
        checkAnswer=view.findViewById(R.id.buttonCheck)
        option1=view.findViewById(R.id.option1)
        option2=view.findViewById(R.id.option2)
        option3=view.findViewById(R.id.option3)
        option4=view.findViewById(R.id.option4)
        questionText=view.findViewById(R.id.question_text)
        timerText=view.findViewById(R.id.countdown_timer)


        endQuizBtn.setOnClickListener {
            userInputListener?.onUserInput(UserInputEvent.RETURN_HOME)
        }
        checkAnswer.setOnClickListener {
            userInputListener?.onUserInput(UserInputEvent.PLAY_DAILYQUIZ)
        }


    }

    fun showQuestion(queText:String,option1Text:String,
                     option2Text:String,option3Text:String,
                     option4Text:String)
    {
        Log.d("Fragment", "Quiz accessed: $queText")
        questionText.text=queText
        option1.text=option1Text
        option2.text=option2Text
        option3.text=option3Text
        option4.text=option4Text


    }


}

