package com.example.QuizBattle.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.QuizBattle.R
import com.google.firebase.messaging.FirebaseMessaging


/**

The GameActivity class is an Android Activity that serves as the main entry point for the quiz-based game.
It is responsible for initializing the GameEngine and acts as a bridge between the user interface and the
game's core logic. It implements the EventListener interface to handle UserInputEvents, delegating
the event handling to the GameEngine.
 */

class GameActivity : AppCompatActivity(), EventListener {
     lateinit var gameEngine: GameEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        gameEngine = GameEngine(this, lifecycleScope, this)
        FirebaseMessaging.getInstance().subscribeToTopic("new_quiz");
    }

    override fun onUserInput(event: UserInputEvent) {
        gameEngine.onUserInput(event)
    }
}
