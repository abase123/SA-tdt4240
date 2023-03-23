package com.example.QuizBattle.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.QuizBattle.view.ui.theme.TestProjectTheme


@Composable
fun HomeScreen (paddingValues: PaddingValues?) {
    var topPadding: Dp = 0.dp
    var bottomPadding: Dp = 0.dp

    if (paddingValues != null) {
        topPadding = paddingValues.calculateTopPadding()
        bottomPadding = paddingValues.calculateBottomPadding()
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(top = topPadding, bottom = bottomPadding),
        contentAlignment = Alignment.Center)
    {
            Text(
                text = "HomeScreen",
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
    }
}

@Composable
fun GreetingsCard() {
    
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
    TestProjectTheme {
        HomeScreen(null)
    }
}