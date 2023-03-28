package com.example.QuizBattle.controller

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import java.security.AccessControlContext

interface GameState {

    abstract fun handleView(activity: MenuActivity)

}