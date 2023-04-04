package com.example.QuizBattle.controller.statePattern

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.QuizBattle.controller.Game
import java.security.AccessControlContext

interface GameState {
   abstract fun handle(context: Game)
}