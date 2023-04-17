package com.example.QuizBattle.controller


enum class UserInputEvent{
    PLAY_DAILYQUIZ,
    PLAY_FRIEND,
    LOAD_DAILY_QUIZ,
    RESULTS,
    RETURN_HOME
}

interface ViewChangeListener {
    abstract fun onUserInput(event:UserInputEvent)
}