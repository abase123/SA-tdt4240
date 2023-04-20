package com.example.QuizBattle.model.OnlineModel;

import com.example.QuizBattle.model.PlayerModel.Player
import com.example.QuizBattle.model.QuizModel.GainedPoints
import com.example.QuizBattle.model.QuizModel.Quiz

class OnlineMatch {
    lateinit var id: String
    lateinit var playerOne: Player
    lateinit var playerTwo: Player
    private lateinit var GainedPointsPlayerOne: GainedPoints
    private lateinit var GainedPointsPlyerTwo: GainedPoints
    private lateinit var quiz: Quiz
    lateinit var gameStatus: String
}
