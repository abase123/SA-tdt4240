package com.example.QuizBattle.model.QuizModel
/**

GainedPoints is a class that keeps track of the score and the number of correct answers during a quiz.

@property score The current score of the user during the quiz.

@property numCorrectAnswer The current number of correct answers given by the user during the quiz.
 */
class GainedPoints(private var points:Int=0, private var numCorrectAnswer:Int=0 ) {
    fun addPoints(newPoint:Int){
        points += newPoint
    }
    fun getPoints():Int{
        return points
    }
    fun resetPoints(){
        points = 0
    }

    fun incrementNumCorrectAnswer(){
        numCorrectAnswer++
    }

    fun resetNumCorrectAnswer(){
        numCorrectAnswer=0
    }

    fun getNumCorrectAnswer():Int{
        return numCorrectAnswer
    }

}