package com.example.QuizBattle.model.QuizModel

import com.example.QuizBattle.model.QuizModel.MainQuizComponents.Quiz
/**

QuizHolder is a data class that holds the necessary information about a quiz, including the chosen theme,
the quiz object, the points gained during the quiz, and the quiz timer.
@property chosenTheme The theme chosen for the quiz.
@property quiz The Quiz object containing the quiz questions and answers.
@property gainedPoints The GainedPoints object representing the points gained during the quiz.
@property timer The QuizTimer object responsible for tracking the time taken to complete the quiz.
 */
data class QuizHolder(var chosenTheme:String, var quiz: Quiz, var gainedPoints: GainedPoints, val timer:QuizTimer)




