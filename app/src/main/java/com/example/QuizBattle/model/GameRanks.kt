package com.example.QuizBattle.model

private val rankNames = mapOf(
    "Noob" to (0..20),
    "Smarty_Pants" to (21..100),
    "Brianic" to (101..300),
    "Trivia Titan" to (301..800),
    "Supreme QuizMaster" to (801 ..2000))
fun getRankForScore(score: Int): String {
    for ((rank, range) in rankNames) {
        if (score in range) {
            return rank
        }
    }
    return "GO FIND GOD! YOU HAVE MASTERED THE GAME."
}