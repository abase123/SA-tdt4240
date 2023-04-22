package com.example.QuizBattle.controller

import com.example.QuizBattle.model.QuizModel.QuizHolder
/**
The GameState interface represents a specific game state within the quiz-based game. It provides a handleState
method that takes a GameActivity context as a parameter, allowing the implementing class to execute the logic
associated with the particular state. The GameState interface also includes a quizHolder property, which is used
to store and manage the current QuizHolder object throughout the game.
*/

interface GameState {
   fun handleState(context: GameActivity)
   var quizHolder: QuizHolder

}