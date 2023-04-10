package com.example.QuizBattle.model

private val rankNames = mapOf(
    "Noob" to (0..2000),
    "Smarty_Pants" to (2001..4500),
    "Brianic" to (4501..7500),
    "Trivia Titan" to (7501..20000),
    "Supreme QuizMaster" to (20001 ..100000))
fun getRankForScore(score: Int): String {
    for ((rank, range) in rankNames) {
        if (score in range) {
            return rank
        }
    }
    return "GO FIND GOD!"
}