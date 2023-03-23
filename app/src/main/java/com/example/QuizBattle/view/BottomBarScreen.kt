package com.firstexerice.testproject

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen (
    val route: String,
    val title: String,
    val icon: ImageVector

    ) {
    object Home: BottomBarScreen (
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )
    object Friends: BottomBarScreen (
        route = "friends",
        title = "Friends",
        icon = Icons.Default.Person
    )
    object Leaderboard: BottomBarScreen (
        route = "leaderboard",
        title = "Leaderboard",
        icon = Icons.Default.List
    )
}
