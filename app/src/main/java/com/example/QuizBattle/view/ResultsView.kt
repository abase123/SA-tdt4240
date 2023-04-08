package com.example.QuizBattle.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.UserInputEvent
import com.example.QuizBattle.controller.UserInputListener

import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

class ResultsView:Fragment()
{
    private lateinit var backHomeBtn:Button
    private lateinit var totalScore:TextView
    private lateinit var ratingBar:RatingBar
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
        totalScore=view.findViewById(R.id.tv_total_score)



        backHomeBtn.setOnClickListener{
            userInputListener?.onUserInput(UserInputEvent.RETURN_HOME)
        }


        startConfettiAnimation()

    }


    private fun startConfettiAnimation() {
        val konfettiView = requireView().findViewById<nl.dionsegijn.konfetti.KonfettiView>(R.id.konfetti_view)

        konfettiView.build()
            .setDirection(0.0, 359.0) // Set the direction for an explosion effect
            .setSpeed(4f, 18f) // Adjust the speed range for a more explosive effect
            .setFadeOutEnabled(true)
            .setTimeToLive(5200L)
            .addShapes(Shape.Square, Shape.Circle)
            .addSizes(Size(30))
            .setPosition(konfettiView.width / 2f, konfettiView.height / 2f) // Set the explosion center
            .burst(300) // Set the number of particles in the explosion
    }



}