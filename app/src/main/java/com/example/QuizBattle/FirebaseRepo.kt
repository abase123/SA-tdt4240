package com.example.QuizBattle
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


class FirebaseRepo {
    private val database = FirebaseDatabase.getInstance()
    private val quizRef = database.getReference("quiz")

}