package com.example.QuizBattle.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.QuizBattle.view.ui.theme.TestProjectTheme

@Composable
fun FriendsScreen (paddingValues: PaddingValues?) {
    var topPadding: Dp = 0.dp
    var bottomPadding: Dp = 0.dp

    if (paddingValues != null) {
        topPadding = paddingValues.calculateTopPadding()
        bottomPadding = paddingValues.calculateBottomPadding()
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = topPadding, bottom = bottomPadding),
        contentAlignment = Alignment.Center)
    {
        Text(
            text = "Friends",
            fontSize = 30.sp,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FriendsPreview() {
    TestProjectTheme {
        FriendsScreen(null)
    }
}