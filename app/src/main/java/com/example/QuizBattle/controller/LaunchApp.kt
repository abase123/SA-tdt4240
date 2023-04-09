package com.example.QuizBattle.controller

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.gameStates.GameState
import com.google.firebase.auth.FirebaseAuth

class LaunchApp:AppCompatActivity() {
    private lateinit var currentState: GameState
    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
        setContentView(R.layout.loadingcreen)
       // 2000 milliseconds = 2 seconds
        mAuth=FirebaseAuth.getInstance()
        val user= mAuth.currentUser

        Handler().postDelayed({
            if (user!=null) {
                Intent(this,GameController::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
            else{
                Intent(this,SignInActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
            // Code to be executed after the delay
        }, 2000)

    }
}


