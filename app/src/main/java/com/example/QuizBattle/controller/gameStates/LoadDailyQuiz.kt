package com.example.QuizBattle.controller.gameStates

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.QuizBattle.FirebaseRepoDailyQuiz
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.model.QuizModel.QuizHolder
import com.example.QuizBattle.view.LoadingQuizView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class LoadDailyQuiz(override var quizHolder: QuizHolder): GameState {
    private val firebaseRepo:FirebaseRepoDailyQuiz=FirebaseRepoDailyQuiz()

    override fun handle(context: GameController) {
        val quizId = getTodaysQuizID()
        loadQuizFromFirebase(context, firebaseRepo, quizId)
    }

    private fun loadQuizFromFirebase(context: GameController, firebaseRepo: FirebaseRepoDailyQuiz, quizId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                Log.d("LoadQuiz", "Quiz accessed: ${quizHolder.quiz}")
                quizHolder.quiz = firebaseRepo.loadQuiz(quizId)
                val questions = firebaseRepo.loadQuestions(quizHolder.quiz)
                quizHolder.quiz.setQuestions(questions)
                updateFragment(context)
            } catch (e: Exception) {
                Toast.makeText(context, "Error loading quiz: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateFragment(context: GameController) {
        val currentFragment = getCurrentFragment(context)
        (currentFragment as? LoadingQuizView)?.onQuizLoaded(quizHolder.quiz.getTheme())
    }

    private fun getCurrentFragment(context: GameController): Fragment {
        val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments[0]
    }

    private fun getTodaysQuizID(): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}