package com.example.QuizBattle.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.QuizBattle.view.screens.FriendsScreen
import com.example.QuizBattle.view.screens.HomeScreen
import com.example.QuizBattle.view.screens.LeaderboardScreen
import com.firstexerice.testproject.BottomBarScreen


@Composable
fun BottomNavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(paddingValues)
        }
        composable(route = BottomBarScreen.Friends.route) {
            FriendsScreen(paddingValues)
        }
        composable(route = BottomBarScreen.Leaderboard.route) {
            LeaderboardScreen(paddingValues)
        }
    }
    
}