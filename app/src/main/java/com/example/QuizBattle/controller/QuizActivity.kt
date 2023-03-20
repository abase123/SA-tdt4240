package com.example.QuizBattle.controller

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.QuizBattle.R

class QuizActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question)
        val questionText: TextView=findViewById(R.id.que)

        val q : String ="A question here ...?"
        questionText.text = q

        }

}
