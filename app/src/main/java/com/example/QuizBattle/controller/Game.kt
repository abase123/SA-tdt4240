package com.example.QuizBattle.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.QuizBattle.controller.statePattern.*
import com.google.firebase.firestore.auth.User


class Game :AppCompatActivity(),UserInputListener{
    private var state: GameState?=null

    private fun newState(newState: GameState){
        state=newState
        state?.handle(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newState(StartMenu())

    }
    override fun onUserInput(event: UserInputEvent) {
        when(event){
            UserInputEvent.PLAY_DAILYQUIZ->newState(DailyQuiz())
            UserInputEvent.PLAY_FRIEND->newState(FriendQuiz())
            UserInputEvent.PLAY_SOLO->newState(SoloQuiz())
            UserInputEvent.RETURN_Home->newState(StartMenu())

        }
    }


}