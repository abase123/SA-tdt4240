package com.example.QuizBattle
import com.example.QuizBattle.model.Option
import com.example.QuizBattle.model.Question
import com.example.QuizBattle.model.Quiz
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class FirebaseRepo() {

    private val database = FirebaseFirestore.getInstance()
    private val quizRef = database.collection("quizzes")

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

    suspend fun loadQuestions(quiz:Quiz):MutableList<Question>{
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
        val question=Question(questionId,questionText,correctAnswer)

        val option1 = Option(document.get("option1") as String)
        val option2 = Option(document.get("option2") as String)
        val option3 = Option(document.get("option3") as String)
        val option4 = Option(document.get("option4") as String)

        question.addOption(option1)
        question.addOption(option2)
        question.addOption(option3)
        question.addOption(option4)

        return question

    }



}