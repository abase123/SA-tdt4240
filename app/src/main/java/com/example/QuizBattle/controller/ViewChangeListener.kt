package com.example.QuizBattle.controller


enum class UserInputEvent{
    PLAY_DAILY_QUIZ,
    FIND_OPPONENT,
    LOAD_DAILY_QUIZ,
    RESULTS,
    RETURN_HOME
}

interface ViewChangeListener {
     fun onUserInput(event:UserInputEvent)
}