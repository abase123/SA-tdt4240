package com.example.QuizBattle
import com.example.QuizBattle.controller.statePattern.DataLoadListener
import com.example.QuizBattle.model.Question
import com.example.QuizBattle.model.Quiz
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class FirebaseRepo(private val dataLoadListener: DataLoadListener) {

    private val database = FirebaseFirestore.getInstance()
    private val quizRef = database.collection("quiz")

    suspend fun loadQuiz(quizId: String): Quiz {
        return suspendCoroutine { continuation ->
            quizRef.document(quizId).get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val quiz = documentSnapshotToQuiz(documentSnapshot)
                    continuation.resume(quiz)
                } else {
                    continuation.resumeWithException(Exception("Quiz not found"))
                }
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
    }

    private fun documentSnapshotToQuiz(snapshot: DocumentSnapshot): Quiz {

    }






}