package com.example.QuizBattle.controller

import android.widget.Button
import android.widget.TextView
import com.example.QuizBattle.R

class QuizActivity: GameState  {


    /*override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question)
        val questionText: TextView=findViewById(R.id.que)
        val q : String ="A question here ...?"
        questionText.text = q

        }*/
    private lateinit var goback: Button
    override fun handleView(activity: MenuActivity) {
        activity.setContentView(R.layout.question)
        val questionText:TextView=activity.findViewById(R.id.que)
        val q : String ="A question here ...?"
        questionText.text = q
        goback=activity.findViewById(R.id.GoBackQuiz)
        goback.setOnClickListener {
            // Transition to the next state
            activity.setState(HomeMenuActivity())
        }
    }

}
