package com.example.QuizBattle.controller


enum class UserInputEvent{

    PLAY_DAILYQUIZ,
    PLAY_FRIEND,
    PLAY_SOLO,
    RETURN_Home
}


interface UserInputListener {
    abstract fun onUserInput(event:UserInputEvent)
}