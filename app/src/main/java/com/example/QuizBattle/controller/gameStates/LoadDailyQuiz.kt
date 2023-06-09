package com.example.QuizBattle.controller.gameStates


import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.QuizBattle.R
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoQuiz
import com.example.QuizBattle.controller.GameActivity
import com.example.QuizBattle.controller.GameCoreController.GameState
import com.example.QuizBattle.model.QuizModel.QuizHolder
import com.example.QuizBattle.viewControllers.LoadingDailyQuizView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
/**

LoadDailyQuiz is a class responsible for loading the daily quiz from Firestore
based on the current date. This class handles the state of loading the daily
quiz, updates the quiz holder with the fetched quiz data, and interacts with
the LoadingDailyQuizView class to show the status of the quiz loading process.
@property quizHolder Holds the current quiz data, including the quiz ID.
 */

class LoadDailyQuiz(override var quizHolder: QuizHolder): GameState {
    private val repoDailyQuiz: FirestoreRepoQuiz = FirestoreRepoQuiz("daily_quizzes")
    override fun handleState(context: GameActivity) {
        // In the real application the getTodaysQuizId will be called to find the active quiz.
        val quizId = "20230412" //getTodaysQuizID()
        loadQuizFromFirebase(context, repoDailyQuiz, quizId)
    }

    private fun loadQuizFromFirebase(context: GameActivity, firebaseRepo: FirestoreRepoQuiz, quizId: String) {
        context.lifecycleScope.launch{
            try {
                withContext(Dispatchers.IO){ Log.d("LoadQuiz", "Quiz accessed: ${quizHolder.quiz}")
                    quizHolder.quiz = firebaseRepo.loadQuizById(quizId)
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


    private fun onQuizAvailable(context: GameActivity, theme:String, difficulty:String) {
        val currentFragment=getCurrentFragment(context)
        (currentFragment as? LoadingDailyQuizView)?.onQuizLoaded(theme,difficulty)
    }

    private fun onQuizNotAvailable(context: GameActivity) {
        val currentFragment=getCurrentFragment(context)
        (currentFragment as? LoadingDailyQuizView)?.onQuizNotAvailable()
    }


    override fun getCurrentFragment(context: GameActivity): Fragment {
        val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments[0]
    }

}