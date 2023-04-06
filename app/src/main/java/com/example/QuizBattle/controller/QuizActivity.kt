package com.example.QuizBattle.controller

import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import com.example.QuizBattle.R

class QuizActivity: GameState  {

    override fun handleView(activity: MenuActivity) {
        activity.setContentView(R.layout.quiz)

        val questionText: TextView = activity.findViewById(R.id.question_text)
        val question: String = "A question here ...?"
        questionText.text = question

        // RadioButtons
        val answerA: RadioButton = activity.findViewById(R.id.answer_A)
        val answerB: RadioButton = activity.findViewById(R.id.answer_B)
        val answerC: RadioButton = activity.findViewById(R.id.answer_C)
        val answerD: RadioButton = activity.findViewById(R.id.answer_D)

        val radioButtons = listOf(answerA, answerB, answerC, answerD)

        // Set up RadioButton click listeners
        for (radioButton in radioButtons) {
            radioButton.setOnClickListener {
                for (otherRadioButton in radioButtons) {
                    if (otherRadioButton != it) {
                        otherRadioButton.isChecked = false
                    }
                }
            }
        }

        // Submit button
        val submitButton: Button = activity.findViewById(R.id.submit_button)
        submitButton.setOnClickListener {
            // Transition to the next state (HomeMenuActivity in this case)
            activity.setState(HomeMenuActivity())
        }
    }
}
