package com.example.QuizBattle.controller

import android.os.Bundle
import android.widget.TextView
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.MainActivity

class QuizActivity: MainActivity()  {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question)
        val questionText: TextView=findViewById(R.id.que)
        val q : String ="A question here ...?"
        questionText.text = q

        }

}
