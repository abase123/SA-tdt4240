package com.example.QuizBattle.controller

import OnThemeChangeListener
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.QuizBattle.R


/**

The GameController class is an Android Activity that serves as the main entry point for the quiz-based game.
It is responsible for initializing the GameEngine and acts as a bridge between the user interface and the
game's core logic. It implements the ViewChangeListener interface to handle UserInputEvents, delegating
the event handling to the GameEngine.
 */

class GameController : AppCompatActivity(), ViewChangeListener {
     lateinit var gameEngine: GameEngine
     private val notificationService=NotificationService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        gameEngine = GameEngine(this, lifecycleScope, this)
    }

    override fun onUserInput(event: UserInputEvent) {
        gameEngine.onUserInput(event)
    }
}
