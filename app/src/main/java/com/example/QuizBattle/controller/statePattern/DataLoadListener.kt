package com.example.QuizBattle.controller.statePattern

import com.example.QuizBattle.model.Quiz

interface DataLoadListener {
    abstract fun  onDataLoaded(quiz:Quiz)
}