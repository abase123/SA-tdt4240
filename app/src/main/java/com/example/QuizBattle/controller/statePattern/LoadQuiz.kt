package com.example.QuizBattle.controller.statePattern

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.QuizBattle.FirebaseRepo
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.Game
import com.example.QuizBattle.model.Question
import com.example.QuizBattle.model.Quiz
import com.example.QuizBattle.view.Home
import com.example.QuizBattle.view.LoadingQuiz
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class LoadQuiz : GameState {
    private lateinit var dailyQuiz: Quiz

    override fun handle(context: Game) {
        val firebaseRepo = FirebaseRepo()
        val quizId = getTodaysQuizID()

        loadQuizFromFirebase(context, firebaseRepo, quizId)
    }
    private fun loadQuizFromFirebase(context: Game, firebaseRepo: FirebaseRepo, quizId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                Log.d("FirebaseRepo", "Loading questions for quiz with ID:")
                dailyQuiz = firebaseRepo.loadQuiz(quizId)
                val questions = firebaseRepo.loadQuestions(dailyQuiz)
                dailyQuiz.setQuestions(questions)
                updateFragment(context)
            } catch (e: Exception) {
                Toast.makeText(context, "Error loading quiz: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun updateFragment(context: Game) {
        val currentFragment = getCurrentFragment(context)
        (currentFragment as? LoadingQuiz)?.onQuizLoaded(dailyQuiz.getTheme())
    }

    private fun getCurrentFragment(context: Game): Fragment {
        val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments[0]
    }

    private fun getTodaysQuizID(): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}