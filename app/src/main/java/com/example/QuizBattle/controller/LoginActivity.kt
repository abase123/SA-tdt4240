package com.example.QuizBattle.controller

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.QuizBattle.R
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth

class LoginActivity:AppCompatActivity() {
    private lateinit var loginButton: Button
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        loginButton=findViewById(R.id.google_sign_in_button)

        mAuth=FirebaseAuth.getInstance()
        val user= mAuth.currentUser

        if (user!=null) {
            val mainActivity = Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
        else{
            val sigIN= Intent(this,loginButton::class.java).also { startActivity(it) }
        }


        Handler().postDelayed({},2000)


    }
}


