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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.controller.UserInputEvent
import com.example.QuizBattle.controller.UserInputListener
import com.example.QuizBattle.model.QuizModel.GainedPoints
import com.google.android.material.color.utilities.Score

import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

class ResultsView:Fragment()
{
    private lateinit var backHomeBtn:Button
    private lateinit var totalScore:TextView
    private lateinit var rankText:TextView
    private lateinit var pointsGainedText:TextView
    private lateinit var ratingBar:RatingBar
    private lateinit var timeUsedText:TextView
    private var userInputListener: UserInputListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backHomeBtn=view.findViewById(R.id.BackHome)
        ratingBar=view.findViewById(R.id.ratingBar)
        rankText=view.findViewById(R.id.rankText)
        pointsGainedText=view.findViewById(R.id.pointsGainedText)
        totalScore=view.findViewById(R.id.tv_total_score)
        timeUsedText=view.findViewById(R.id.timeUsedText)
        backHomeBtn.visibility= View.INVISIBLE

        backHomeBtn.setOnClickListener{
            userInputListener?.onUserInput(UserInputEvent.RETURN_HOME)
        }
    }


    fun presentQuizResults(score:Int ,newRank:String,pointsGained:GainedPoints, timeUsed:Float){
        showGainedPoints(pointsGained)
        showTimeUsed(timeUsed)
        Handler(Looper.getMainLooper()).postDelayed({
            animateScoreBar(pointsGained.getScore())
        }, 1000)

        Handler(Looper.getMainLooper()).postDelayed({
            setScore(score)
            setRank(newRank)
            startConfettiAnimation()
        }, 1000)

        startConfettiAnimation()
        backHomeBtn.visibility=View.VISIBLE
    }


    @SuppressLint("SetTextI18n")
    private fun showTimeUsed(timeUsed: Float){
        timeUsedText.text="Quiz time: $timeUsed"
    }

    @SuppressLint("SetTextI18n")
    private fun showGainedPoints(pointsGained: GainedPoints){
        val targetNumber = pointsGained.getScore()
        val animationDuration=2000L

        val valueAnimator = ValueAnimator.ofInt(0, targetNumber).apply {
            duration = animationDuration
            addUpdateListener { animation ->
                val currentValue = animation.animatedValue as Int
                pointsGainedText.text = "Points achieved: $targetNumber"
            }
        }
        // Start the animation
        valueAnimator.start()
    }

    @SuppressLint("SetTextI18n")
    fun setScore(score:Int){
        totalScore.text="Total Score: $score"
    }
    @SuppressLint("SetTextI18n")
    fun setRank(rank:String){
        rankText.text="rank: $rank"
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
        val startRating=score/200f
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