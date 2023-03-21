package com.example.QuizBattle.controller

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity() {

    private lateinit var currentState: GameState

    fun setState(newstate:GameState){
        currentState=newstate
        currentState.handleView(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setState(HomeMenuActivity())
    }




}