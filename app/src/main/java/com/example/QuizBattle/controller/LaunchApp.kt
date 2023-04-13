package com.example.QuizBattle.controller

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.QuizBattle.FireBaseRepoUser
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.gameStates.GameState
import com.example.QuizBattle.model.Player
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LaunchApp:AppCompatActivity() {
    private lateinit var currentState: GameState
    private lateinit var mAuth:FirebaseAuth
    private val firebaseRepoUser: FireBaseRepoUser = FireBaseRepoUser()
    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
        setContentView(R.layout.loadingcreen)
       // 2000 milliseconds = 2 seconds
        mAuth=FirebaseAuth.getInstance()
        val user= mAuth.currentUser

        Handler().postDelayed({
            if (user!=null) {
                Intent(this,GameController::class.java).also {
                    lifecycleScope.launch{
                        addUserToFireStore(user.uid)
                    }
                    startActivity(it)
                    finish()
                }
            }
            else {
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
            val newPlayer = Player(
                displayName = user?.displayName ?: "",
                email = user?.email ?: "",
                score = 0,
                dailyQuizTaken = false,
                ranking="Noob"
            )
            firebaseRepoUser.addUser(newPlayer, uid)
        }
    }
}


