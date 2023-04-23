package com.example.QuizBattle.controller.gameStates


import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameActivity
import com.example.QuizBattle.controller.GameCoreController.GameState
import com.example.QuizBattle.framgmentsControllers.LoadingPlaygroundQuizView
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoQuiz
import com.example.QuizBattle.model.QuizModel.QuizHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
/**

LoadPlayGroundQuiz is a class responsible for loading a playground quiz
from Firestore based on the chosen theme by the user. This class handles
the state of loading the playground quiz, updates the quiz holder with
the fetched quiz data, and interacts with the LoadingPlaygroundQuizView to show the status of
the quiz loading process.
@property quizHolder Holds the current quiz data, including the chosen theme.
 */
class LoadPlayGroundQuiz(override var quizHolder: QuizHolder) : GameState {
    private val repoPlayGroundQuiz: FirestoreRepoQuiz = FirestoreRepoQuiz("playGround_quiz")
    override fun handleState(context: GameActivity) {
        loadQuizFromFirebase(context, repoPlayGroundQuiz, quizHolder.chosenTheme)
    }

    private fun loadQuizFromFirebase(context: GameActivity, firebaseRepo: FirestoreRepoQuiz, quizTheme: String) {
        context.lifecycleScope.launch{
            try {
                withContext(Dispatchers.IO){ Log.d("LoadQuiz", "Quiz accessed: ${quizHolder.quiz}")
                    quizHolder.quiz = firebaseRepo.loadQuizByTheme(quizTheme)
                    val questions = firebaseRepo.loadQuestions(quizHolder.quiz)
                    quizHolder.quiz.setQuestions(questions)
                }
                onQuizAvailable(context,quizHolder.quiz.getTheme(),quizHolder.quiz.getDiff())
            } catch (e: Exception) {
                onQuizNotAvailable(context)
            }
        }
    }
    private fun onQuizAvailable(context: GameActivity, theme:String, difficulty:String) {
        val currentFragment=getCurrentFragment(context)
        (currentFragment as? LoadingPlaygroundQuizView)?.onQuizLoaded(theme,difficulty)
    }

    private fun onQuizNotAvailable(context: GameActivity){
        val currentFragment=getCurrentFragment(context)
        (currentFragment as? LoadingPlaygroundQuizView)?.onQuizNotAvailable()
    }
    private fun getCurrentFragment(context: GameActivity): Fragment {
        val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments[0]
    }
}