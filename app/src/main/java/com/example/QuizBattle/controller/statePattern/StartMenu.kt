package com.example.QuizBattle.controller.statePattern

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.Game
import com.google.android.material.bottomnavigation.BottomNavigationView
class StartMenu:GameState {
    override fun handle(context: Game) {
        context.setContentView(R.layout.main_activity)
        val bottomNavigationView = context.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.mainPageFragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
    }

}