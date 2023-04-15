package com.example.QuizBattle.model.FirestoreRepoes
import com.example.QuizBattle.model.QuizModel.Option
import com.example.QuizBattle.model.QuizModel.Question
import com.example.QuizBattle.model.QuizModel.Quiz
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FireStoreRepoDailyQuiz() {

    private val database = FirebaseFirestore.getInstance()
    private val quizRef = database.collection("daily_quizzes")
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
        val id = snapshot.id
        val type = snapshot.get("Type") as String
        val theme = snapshot.get("Theme") as String
        val diff = snapshot.getString("Diff") as String
        return Quiz(type, id, diff, theme)
    }
    suspend fun loadQuestions(quiz: Quiz):MutableList<Question>{
        return suspendCoroutine { continuation ->
            quizRef.document(quiz.getId()).collection("questions").get()
                .addOnSuccessListener { querySnapshot ->
                    val questions = querySnapshot.documents.map { document ->
                        documentToQuestion(document)
                    }.toMutableList()
                    continuation.resume(questions)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }
    private fun documentToQuestion(document: DocumentSnapshot): Question {
        val questionId = document.id
        val questionText = document.get("text") as String
        val correctAnswer = document.get("correctAnswer") as String
        val question= Question(questionId,questionText,correctAnswer)
        val options = listOf(
            Option(document.getString("option1") as String),
            Option(document.getString("option2") as String),
            Option(document.getString("option3") as String),
            Option(document.getString("option4") as String)
        )
        options.forEach { option -> question.addOption(option) }
        return question

    }



}