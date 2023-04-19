package com.example.QuizBattle.controller


enum class UserInputEvent{
    PLAY_DAILY_QUIZ,
    PLAY_FRIEND,
    LOAD_DAILY_QUIZ,
    RESULTS,
    RETURN_HOME
}

interface ViewChangeListener {
     fun onUserInput(event:UserInputEvent)
}