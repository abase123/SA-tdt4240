package com.example.QuizBattle.controller.LaunchActivities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoUser
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameController
import com.example.QuizBattle.controller.GameState
import com.example.QuizBattle.model.PlayerModel.Player
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

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
                Intent(this, GameController::class.java).also {
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

    @SuppressLint("SuspiciousIndentation")
    private suspend fun addUserToFireStore(uid: String) {
        val userSnapshot = firebaseRepoUser.getUser(uid)
            if (!userSnapshot.exists()) {
                val user = mAuth.currentUser
                val newPlayer = Player(
                    userUid = uid,
                    displayName = user?.displayName ?: "",
                    userEmail = user?.email ?: "",
                    allTimeScore = 0,
                    dailyQuizTaken = false
                )
            firebaseRepoUser.addUser(newPlayer, uid)
            }
        }
}




