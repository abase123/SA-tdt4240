package com.example.QuizBattle.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.QuizBattle.QuizBattleApplication
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameCoreController.EventListener
import com.example.QuizBattle.controller.GameCoreController.GameEngine
import com.example.QuizBattle.controller.GameCoreController.UserInputEvent


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

        val myApp = applicationContext as QuizBattleApplication
        gameEngine = myApp.gameEngineFactory(this, lifecycleScope, this)
    }

    override fun onUserInput(event: UserInputEvent) {
        gameEngine.onUserInput(event)
    }
}