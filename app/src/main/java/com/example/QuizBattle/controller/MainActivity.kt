package com.example.QuizBattle.controller

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.QuizBattle.R
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth

class MainActivity:AppCompatActivity() {
    private lateinit var loginButton:SignInButton
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
        setContentView(R.layout.loadingcreen)

       // 2000 milliseconds = 2 seconds

        mAuth=FirebaseAuth.getInstance()
        val user= mAuth.currentUser

        Handler().postDelayed({
            if (user!=null) {
                Intent(this, MenuActivity::class.java).also {
                    startActivity(it)
                }
            }
            else{
                Intent(this,SignInActivity::class.java).also { startActivity(it) }
            }
            // Code to be executed after the delay
        }, 2000)



    }
}


