package com.example.QuizBattle.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.QuizViewModel
import com.example.QuizBattle.controller.gameStates.DailyQuiz
import com.google.android.material.animation.AnimationUtils


class QuizView : Fragment() {

    private lateinit var quizViewModel: QuizViewModel
    private lateinit var questionText: TextView
    private val options = mutableListOf<RadioButton>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz_nima, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val optionIds = listOf(R.id.answer_A, R.id.answer_B, R.id.answer_C, R.id.answer_D)
        options.addAll(optionIds.map { view.findViewById<RadioButton>(it) })

        with(view) {
            findViewById<RadioGroup>(R.id.answer_group).clearCheck()
            questionText = findViewById(R.id.question_text)
            options.forEach { option ->
                option.setOnClickListener {
                    startScaleAnimation()
                    quizViewModel.updateChosenOption(option.text.toString())
                    quizViewModel.updateQuizStage(DailyQuiz.QuizStage.CHECK_ANSWER)
                }
            }
        }

        quizViewModel = ViewModelProvider(requireActivity()).get(QuizViewModel::class.java)

        quizViewModel.quizStage.observe(viewLifecycleOwner, Observer { newStage ->
            quizViewModel.dailyQuiz.currStage = newStage
            quizViewModel.triggerQuizLoop()
        })

        quizViewModel.currentQuestion.observe(viewLifecycleOwner, Observer { question ->
            showQuestion(
                question.getQuestionText(),
                *question.getOptions().map { it.getOptionText() }.toTypedArray()
            )
        })
    }

    private fun View.startScaleAnimation() {
        val scaleAnimation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.scale_animation)
        startAnimation(scaleAnimation)
    }

    private fun showQuestion(queText: String, vararg optionTexts: String) {
        questionText.text = queText
        optionTexts.forEachIndexed { index, optionText ->
            options[index].text = optionText
        }
    }
}
