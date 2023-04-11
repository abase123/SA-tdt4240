package com.example.QuizBattle.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.QuizBattle.R

import androidx.core.content.ContextCompat
import com.example.QuizBattle.controller.QuizViewModel
import com.example.QuizBattle.controller.UserInputEvent
import com.example.QuizBattle.controller.UserInputListener


class QuizView: Fragment(){
    private lateinit var quizViewModel: QuizViewModel
    private lateinit var questionText: TextView
    private val options = mutableListOf<RadioButton>()
    private lateinit var correctOption:RadioButton
    private var userInputListener: UserInputListener? = null

    private lateinit var rootView: ViewGroup
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView= inflater.inflate(R.layout.fragment_quiz, container, false) as ViewGroup
        return rootView
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

    private fun ShowResults(){
        userInputListener?.onUserInput(UserInputEvent.RESULTS)
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val optionIds = listOf(R.id.answer_A, R.id.answer_B, R.id.answer_C, R.id.answer_D)
        options.addAll(optionIds.map { view.findViewById(it) })
        quizViewModel = ViewModelProvider(requireActivity()).get(QuizViewModel::class.java)

        with(view) {
            findViewById<RadioGroup>(R.id.answer_group).clearCheck()
            questionText = findViewById(R.id.question_text)
            options.forEach { option ->
                option.setOnClickListener {
                    val isCorrect = quizViewModel.dailyQuiz.checkAnswer(option.text.toString())
                    quizViewModel.setIsCorrectAnswer(isCorrect)
                    if(isCorrect){
                        option.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correct_answer))
                    }
                    else
                        option.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.incorrect_answer))
                        showCorrectOption()

                    resetButtons(options){
                        quizViewModel.dailyQuiz.getNextQuestion()
                    }

                }
            }
        }
        quizViewModel.currentQuestion.observe(viewLifecycleOwner, Observer { question ->
            setQuestion(
                question.getQuestionText(),question.getCorrectAnswer(),
                *question.getOptions().map { it.getOptionText() }.toTypedArray()
            )
        })
        quizViewModel.quizEnded.observe(viewLifecycleOwner, Observer { quizEnded ->
            if (quizEnded) {
                ShowResults()
            }
        })
    }



    private fun setQuestion(queText: String,correctOptionText:String,vararg optionTexts: String) {
        questionText.text = queText
        optionTexts.forEachIndexed { index, optionText ->
            options[index].text = optionText
            if(options[index].text==correctOptionText){
                correctOption=options[index]
            }
        }
    }


    private fun showCorrectOption(){
        correctOption.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correct_answer))
    }

    private fun resetButtons(options: MutableList<RadioButton>, onNext:()->Unit){
        options.forEach { radioButton ->
            radioButton.isEnabled = false
        }

        Handler(Looper.getMainLooper()).postDelayed({
            options.forEach { radioButton ->
                radioButton.isChecked = false
                radioButton.isEnabled =true
                radioButton.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.unanswered_color))
            }
            onNext()
        }, 700)


    }
}