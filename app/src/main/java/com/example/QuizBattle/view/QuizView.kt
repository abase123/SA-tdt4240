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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.QuizViewModel
import com.example.QuizBattle.controller.UserInputEvent
import com.example.QuizBattle.controller.UserInputListener
import com.example.QuizBattle.controller.gameStates.DailyQuiz

class QuizView: Fragment() {

    private lateinit var quizViewModel: QuizViewModel
    private var userInputListener: UserInputListener? = null

    //private lateinit var endQuizBtn:Button
    private lateinit var checkAnswer:Button
    private lateinit var option1: RadioButton
    private lateinit var option2: RadioButton
    private lateinit var option3: RadioButton
    private lateinit var option4: RadioButton
    private  lateinit var questionText: TextView
    //private lateinit var timerText:TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_nima, container, false)
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

        //endQuizBtn=view.findViewById(R.id.buttonEndQuiz)
        checkAnswer=view.findViewById(R.id.submit_button)
        option1=view.findViewById(R.id.answer_A)
        option2=view.findViewById(R.id.answer_B)
        option3=view.findViewById(R.id.answer_C)
        option4=view.findViewById(R.id.answer_D)
        questionText=view.findViewById(R.id.question_text)

        //timerText=view.findViewById(R.id.countdown_timer)

        checkAnswer.setOnClickListener {
            userInputListener?.onUserInput(UserInputEvent.RETURN_HOME)
        }
        quizViewModel = ViewModelProvider(requireActivity()).get(QuizViewModel::class.java)
        quizViewModel.currentQuestion.observe(viewLifecycleOwner, Observer { question ->
            showQuestion(
                question.getQuestionText(),
                question.getOptions()[0].getOptionText(),
                question.getOptions()[1].getOptionText(),
                question.getOptions()[2].getOptionText(),
                question.getOptions()[3].getOptionText()
            )
        })

    }

    private fun showQuestion(queText:String, option1Text:String,
                             option2Text:String, option3Text:String,
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

