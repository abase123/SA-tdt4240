package com.example.QuizBattle.controller

import OnThemeChangeListener
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.QuizBattle.R


class GameController : AppCompatActivity(), ViewChangeListener {
     lateinit var gameEngine: GameEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        gameEngine = GameEngine(this, lifecycleScope, this)
    }

    override fun onUserInput(event: UserInputEvent) {
        gameEngine.onUserInput(event)
    }
}
