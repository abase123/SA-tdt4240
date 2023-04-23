package com.example.QuizBattle.controller.LaunchActivities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoUser
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameActivity
import com.example.QuizBattle.controller.GameCoreController.GameState
import com.example.QuizBattle.model.PlayerModel.Player
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
/**
 * Main activity for launching the game application.
 *
 * This activity handles launching the game application and redirecting the user to either the `GameActivity` or `SignInActivity`, depending on whether the user is currently signed in. It uses a `Handler` to delay the transition for two seconds, and it also includes a method for adding a new user to Firestore if they don't already exist. This class extends the Android `AppCompatActivity` class and is designed to be used with the Android framework.
 *
 * @constructor Creates a new instance of the `LaunchApp` class.
 */

class LaunchApp:AppCompatActivity() {
    private lateinit var currentState: GameState
    private lateinit var mAuth:FirebaseAuth
    private val firebaseRepoUser: FirestoreRepoUser = FirestoreRepoUser()
    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
        setContentView(R.layout.loadingcreen)
       // 2000 milliseconds = 2 seconds
        mAuth=FirebaseAuth.getInstance()
        val user= mAuth.currentUser
        Handler().postDelayed({
            if (user!=null) {
                Intent(this, GameActivity::class.java).also {
                    lifecycleScope.launch{
                        addUserToFireStore(user.uid)
                    }
                    startActivity(it)
                    finish()
                }
            }
            else{
                Intent(this, SignInActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
            // Code to be executed after the delay
        }, 2000)

    }


    private suspend fun addUserToFireStore(uid: String) {
        val userSnapshot = firebaseRepoUser.getUser(uid)
        if (!userSnapshot.exists()) {
            val user = mAuth.currentUser
            val friendListJson = userSnapshot.getString("friends")
            val newPlayer = Player(
                userUid = uid,
                displayName = user?.displayName ?: "",
                userEmail = user?.email ?: "",
                allTimeScore = 0,
                dailyQuizTaken = false,
                numQuizzesTaken = 0
            )
            firebaseRepoUser.addUser(newPlayer, uid)
            }
        }
}




