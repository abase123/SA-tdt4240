package com.example.QuizBattle.controller.statePattern

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.QuizBattle.controller.Game
import java.security.AccessControlContext

interface GameState {
   abstract fun handle(context: Game)
}