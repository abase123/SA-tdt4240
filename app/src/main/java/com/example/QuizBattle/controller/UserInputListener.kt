package com.example.QuizBattle.controller


enum class UserInputEvent{
    PLAY_DAILYQUIZ,
    PLAY_FRIEND,
    LOAD_DAILY_QUIZ,
    RETURN_HOME
}


interface UserInputListener {
    abstract fun onUserInput(event:UserInputEvent)
}