package com.example.QuizBattle.model.QuizModel

import com.example.QuizBattle.model.QuizModel.MainQuizComponents.Quiz

data class QuizHolder(var chosenTheme:String, var quiz: Quiz, var gainedPoints: GainedPoints, val timer:QuizTimer)




