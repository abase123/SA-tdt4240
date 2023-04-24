package com.example.QuizBattle.model.FirestoreRepoes
import com.example.QuizBattle.model.QuizModel.*
import com.example.QuizBattle.model.QuizModel.MainQuizComponents.Option
import com.example.QuizBattle.model.QuizModel.MainQuizComponents.Question
import com.example.QuizBattle.model.QuizModel.MainQuizComponents.Quiz
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


/**
FirestoreRepoQuiz is a class responsible for fetching quiz and question data from the Firestore database.
It provides functions to load a quiz by its ID, to load a quiz by its theme, and to load questions for a given quiz.
This class also contains helper functions to convert document snapshots to Quiz and Question objects.
@param dataBasePath The path to the Firestore collection containing quiz data.
 */
class FirestoreRepoQuiz(dataBasePath:String) {

    private val database = FirebaseFirestore.getInstance()
    private val quizRef = database.collection(dataBasePath)

    suspend fun loadQuizById(quizId: String): Quiz {
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

    suspend fun loadQuizByTheme(theme: String): Quiz {
        return suspendCoroutine { continuation ->
            quizRef.whereEqualTo("Theme", theme).limit(1).get()
                .addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.documents.isNotEmpty()) {
                        val documentSnapshot = querySnapshot.documents.first()
                        val quiz = documentSnapshotToQuiz(documentSnapshot)
                        continuation.resume(quiz)
                    } else {
                        continuation.resumeWithException(Exception("Quiz not found"))
                    }
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    private fun documentSnapshotToQuiz(snapshot: DocumentSnapshot): Quiz {
        val id = snapshot.id
        val type = snapshot.get("Type") as String
        val theme = snapshot.get("Theme") as String
        val diff = snapshot.getString("Diff") as String

        val quizBuilder = QuizBuilder(type, id, diff, theme)
        return quizBuilder.build()
    }

    suspend fun loadQuestions(quiz: Quiz): MutableList<Question> {
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
        val questionText = document.get("text") as String
        val correctAnswer = document.get("correctAnswer") as String

        val questionBuilder = QuestionBuilder(questionText, correctAnswer)

        val options = listOf(
            Option(document.getString("option1") as String),
            Option(document.getString("option2") as String),
            Option(document.getString("option3") as String),
            Option(document.getString("option4") as String)
        )

        options.forEach { option -> questionBuilder.addOption(option) }
        return questionBuilder.build()
    }
}
