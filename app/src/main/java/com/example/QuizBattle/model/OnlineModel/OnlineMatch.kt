package com.example.QuizBattle.model.OnlineModel;

import com.example.QuizBattle.model.PlayerModel.Player
import com.example.QuizBattle.model.QuizModel.GainedPoints
import com.example.QuizBattle.model.QuizModel.Quiz

 class OnlineMatch {
     var id:String?=null
    var playerOne: String? = null
    var playerTwo: String? = null
    var GainedPointsPlayerOne: GainedPoints? = null
    var GainedPointsPlayerTwo: GainedPoints? = null
    var quiz: Quiz? = null
    var gameStatus: String? = null
}
