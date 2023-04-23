package com.example.QuizBattle.controller

/**

UserInputEvent is an enum class that represents various user input events that can occur within the quiz-based game.
Each event corresponds to a specific action or state transition, such as playing the daily quiz or loading a
playground quiz.
EventListener is an interface designed to be implemented by classes that need to respond to UserInputEvents.
It defines a single method, onUserInput, which is called with a UserInputEvent parameter, allowing the implementing
class to handle the event accordingly.
 */

enum class UserInputEvent{
    PLAY_DAILY_QUIZ,
    SELECT_THEME,
    PLAY_PLAYGROUND,
    LOAD_PLAYGROUND_QUIZ,
    LOAD_DAILY_QUIZ,
    RESULTS,
    RETURN_HOME
}

interface EventListener {
     fun onUserInput(event:UserInputEvent)
}