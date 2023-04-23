package com.example.QuizBattle.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameActivity
import com.example.QuizBattle.controller.GameCoreController.UserInputEvent
import com.example.QuizBattle.controller.GameCoreController.EventListener
import com.example.QuizBattle.model.QuizModel.GainedPoints

import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
/**

ResultsView is a Fragment class responsible for displaying the quiz results, including points gained,

the number of correct answers, the time taken, and a rating based on the score.

It also provides an option to return to the home screen.

@property backHomeBtn The Button used for navigating back to the home screen.

@property totalScore The TextView used to display the total score.

@property pointsGainedText The TextView used to display the points gained during the quiz.

@property ratingBar The RatingBar used to display a rating based on the score.

@property timeUsedText The TextView used to display the time taken to complete the quiz.

@property numCorrectTextView The TextView used to display the number of correct answers.

@property eventListener An optional EventListener for handling user input events.

@property gameActivity The GameActivity object to which this fragment is attached.
 */
class ResultsView:Fragment()
{
    private lateinit var backHomeBtn:Button
    private lateinit var totalScore:TextView
    private lateinit var pointsGainedText:TextView
    private lateinit var ratingBar:RatingBar
    private lateinit var timeUsedText:TextView
    private  lateinit var numCorrectTextView: TextView
    private var eventListener: EventListener? = null
    private lateinit var gameActivity: GameActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false)
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


    private fun setup(view: View){
        backHomeBtn=view.findViewById(R.id.BackHome)
        ratingBar=view.findViewById(R.id.ratingBar)
        pointsGainedText=view.findViewById(R.id.pointsGainedText)
        timeUsedText=view.findViewById(R.id.timeUsedText)
        numCorrectTextView=view.findViewById(R.id.num_correct)
        backHomeBtn.visibility= View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
        gameActivity = activity as GameActivity
        gameActivity.gameEngine.fragmentLoading.setLoading(false)
        backHomeBtn.setOnClickListener{
            eventListener?.onUserInput(UserInputEvent.RETURN_HOME)
        }

    }


    fun presentQuizResults(pointsGained:GainedPoints, timeUsed:Float, quizLength:Int ){

        showGainedPoints(pointsGained, quizLength)
        showTimeUsed(timeUsed)
        Handler(Looper.getMainLooper()).postDelayed({
            animateScoreBar(pointsGained.getScore())
        }, 1000)

        Handler(Looper.getMainLooper()).postDelayed({
            startConfettiAnimation()
        }, 1000)

        startConfettiAnimation()
        backHomeBtn.visibility=View.VISIBLE
    }


    @SuppressLint("SetTextI18n")
    private fun showTimeUsed(timeUsed: Float){
        timeUsedText.text="Total Time used: $timeUsed"
    }

    @SuppressLint("SetTextI18n")
    private fun showGainedPoints(pointsGained: GainedPoints, quizLength: Int){
        val targetNumber = pointsGained.getScore()
        val animationDuration=2000L

        val valueAnimator = ValueAnimator.ofInt(0, targetNumber).apply {
            duration = animationDuration
            addUpdateListener { animation ->
                pointsGainedText.text = "Points achieved: $targetNumber"
                numCorrectTextView.text= pointsGained.getNumCorrectAnswer().toString() + "/" + quizLength.toString() + "Correct Answers!"
            }
        }
        // Start the animation
        valueAnimator.start()
    }

    @SuppressLint("SetTextI18n")
    fun setScore(score:Int){
        totalScore.text="Total Score: $score"
    }


    private fun startConfettiAnimation() {
        val konfettiView = requireView().findViewById<nl.dionsegijn.konfetti.KonfettiView>(R.id.konfetti_view)
        konfettiView.build()
            .setDirection(0.0, 359.0) // Set the direction for an explosion effect
            .setSpeed(4f, 18f) // Adjust the speed range for a more explosive effect
            .setFadeOutEnabled(true)
            .setTimeToLive(5200L)
            .addShapes(Shape.Square, Shape.Circle)
            .addSizes(Size(10))
            .setPosition(konfettiView.width / 2f, konfettiView.height / 2f) // Set the explosion center
            .burst(300) // Set the number of particles in the explosion
    }

    private fun animateScoreBar(score: Int){
        Log.d("star", "Quiz accessed: $score")
        val startRating=score/333f
        Log.d("star", "Quiz accessed: $startRating")
        val animationDuration=2000L

        val animator = ValueAnimator.ofFloat(0f, startRating).apply {
            duration = animationDuration
            addUpdateListener { animation ->
                val animatedValue = animation.animatedValue as Float
                ratingBar.rating = animatedValue
            }

        }
        animator.start()

    }

}