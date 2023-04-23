package com.example.QuizBattle.view

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
import com.example.QuizBattle.controller.GameActivity
import com.example.QuizBattle.controller.gameStates.PlayQuizState.PlayQuizViewModel
import com.example.QuizBattle.controller.UserInputEvent
import com.example.QuizBattle.controller.EventListener

/**

QuizView is a Fragment class responsible for displaying the quiz questions and handling user interactions.

It communicates with the PlayQuizViewModel to receive updates on the current question and the quiz status.

It also handles user input events and updates the UI accordingly.

@property playDailyQuizViewModel The ViewModel object used to manage the UI state during the quiz.

@property questionText The TextView used to display the current question text.

@property options A mutable list of RadioButtons used to display the answer options.

@property correctOption The RadioButton representing the correct answer for the current question.

@property eventListener An optional EventListener for handling user input events.

@property gameActivity The GameActivity object to which this fragment is attached.
 */
class QuizView : Fragment() {
    private lateinit var playDailyQuizViewModel: PlayQuizViewModel
    private lateinit var questionText: TextView
    private val options = mutableListOf<RadioButton>()
    private lateinit var correctOption: RadioButton
    private var eventListener: EventListener? = null
    private lateinit var gameActivity: GameActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz, container, false) as ViewGroup
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

    private fun showResults() {
        eventListener?.onUserInput(UserInputEvent.RESULTS)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView(view)

        playDailyQuizViewModel.currentQuestion.observe(viewLifecycleOwner, Observer { question ->
            setQuestion(
                question.getQuestionText(), question.getCorrectAnswer(),
                *question.getOptions().map { it.getOptionText() }.toTypedArray()
            )
        })

        playDailyQuizViewModel.quizEnded.observe(viewLifecycleOwner, Observer { quizEnded ->
            if (quizEnded) {
                showResults()
            }
        })
    }

    private fun setupView(view: View) {

        val optionIds = listOf(R.id.answer_A, R.id.answer_B, R.id.answer_C, R.id.answer_D)
        options.addAll(optionIds.map { view.findViewById(it) })
        playDailyQuizViewModel = ViewModelProvider(requireActivity())[PlayQuizViewModel::class.java]
        with(view) {
            findViewById<RadioGroup>(R.id.answer_group).clearCheck()
            questionText = findViewById(R.id.question_text)
            setupOptionClickListeners(view)
        }

        gameActivity = activity as GameActivity
        gameActivity.gameEngine.fragmentLoadingState.setLoading(false)
    }

    private fun setupOptionClickListeners(view: View) {
        options.forEach { option ->
            option.setOnClickListener {
                handleOptionClick(option)
            }
        }
    }
    private fun handleOptionClick(option: RadioButton) {
        val isCorrect = playDailyQuizViewModel.dailyQuiz.checkAnswer(option.text.toString())
        playDailyQuizViewModel.setIsCorrectAnswer(isCorrect)
        if (isCorrect) {
            option.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correct_answer))
        } else {
            option.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.incorrect_answer))
            showCorrectOption()
        }
        resetButtons(options) {
            playDailyQuizViewModel.dailyQuiz.getNextQuestion()
        }
    }
    private fun setQuestion(queText: String, correctOptionText: String, vararg optionTexts: String) {
        questionText.text = queText
        optionTexts.forEachIndexed { index, optionText ->
            options[index].text = optionText
            if (options[index].text == correctOptionText) {
                correctOption = options[index]
            }
        }
    }

    private fun showCorrectOption() {
        correctOption.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correct_answer))
    }

    private fun resetButtons(options: MutableList<RadioButton>, onNext: () -> Unit) {
        options.forEach { radioButton ->
            radioButton.isEnabled = false
        }
        Handler(Looper.getMainLooper()).postDelayed({
            options.forEach { radioButton ->
                radioButton.isChecked = false
                radioButton.isEnabled = true
                radioButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.unanswered_color))
            }
            onNext()
        }, 700)
    }
}
