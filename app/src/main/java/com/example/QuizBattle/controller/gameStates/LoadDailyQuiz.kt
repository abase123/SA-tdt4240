package com.example.QuizBattle.controller.gameStates

import android.app.AlertDialog
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.QuizBattle.R
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoQuiz
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.controller.GameState
import com.example.QuizBattle.model.QuizModel.QuizHolder
import com.example.QuizBattle.framgmentsControllers.LoadingQuizView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class LoadDailyQuiz(override var quizHolder: QuizHolder): GameState {
    private val repoDailyQuiz: FirestoreRepoQuiz = FirestoreRepoQuiz("daily_quizzes")
    override fun handleState(context: GameController) {
        val quizId = "20230412" //getTodaysQuizID()
        loadQuizFromFirebase(context, repoDailyQuiz, quizId)
    }

    private fun loadQuizFromFirebase(context: GameController, firebaseRepo: FirestoreRepoQuiz, quizId: String) {
        context.lifecycleScope.launch{
            try {
                withContext(Dispatchers.IO){ Log.d("LoadQuiz", "Quiz accessed: ${quizHolder.quiz}")
                    quizHolder.quiz = firebaseRepo.loadQuiz(quizId)
                    val questions = firebaseRepo.loadQuestions(quizHolder.quiz)
                    quizHolder.quiz.setQuestions(questions)
                }
                onQuizAvailable(context,quizHolder.quiz.getTheme(),quizHolder.quiz.getDiff())
            } catch (e: Exception) {
                onQuizNotAvailable(context)

            }
        }
    }

    private fun getTodaysQuizID(): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }


    private fun onQuizAvailable(context: GameController,theme:String,difficulty:String) {
        val currentFragment=getCurrentFragment(context)
        (currentFragment as? LoadingQuizView)?.onQuizLoaded(theme,difficulty)
    }

    private fun onQuizNotAvailable(context: GameController){
        AlertDialog.Builder(context)
            .setTitle("Quiz Not Available")
            .setMessage("The quiz is not available at the moment. ")
            .setPositiveButton("OK", null)
            .show()
    }

    private fun getCurrentFragment(context: GameController): Fragment {
        val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments[0]
    }

}