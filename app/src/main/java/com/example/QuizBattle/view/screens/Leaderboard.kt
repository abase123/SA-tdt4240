package com.example.QuizBattle.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.QuizBattle.R
import com.example.QuizBattle.view.ui.theme.TestProjectTheme


@Composable
fun LeaderboardScreen (paddingValues: PaddingValues?) {
    val players = arrayOf("Hello", "My", "Name", "Is", "Luka", "Luka", "Luka","Luka","Luka","Luka","Luka","Luka","Luka","Luka","Luka","Luka","Luka","Luka","Luka","Luka","Luka","Luka","Luka","Luka")
    var topPadding: Dp = 0.dp
    var bottomPadding: Dp = 0.dp

    if (paddingValues != null) {
        topPadding = paddingValues.calculateTopPadding()
        bottomPadding = paddingValues.calculateBottomPadding()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = topPadding, bottom = bottomPadding),
    ) {
        Text(
            text = "Leaderboard",
            fontSize = 35.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(top = 20.dp)

        )
        Row() {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(top = 10.dp, end = 20.dp)
            ) {
                Icon(Icons.Outlined.Person, contentDescription = "Friends Leaderboard")
                Text(text = "Friends")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(top = 10.dp, start = 20.dp)

            ) {
                //Må endre dette ikonet til noe annet
                Icon(Icons.Outlined.Place, contentDescription = "Friends Leaderboard")
                Text(text = "Global")
            }
        }

        LeaderboardList(players = players)
    }
}

@Composable
fun LeaderboardList(players: Array<String>) {
    LazyColumn (
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp, start = 30.dp, end = 30.dp)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
            .fillMaxSize()
            .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)

            ) {
        items(players.size) {i ->
            LeaderboardItem(name = players[i], place = i+1)
        }
    }
}

@Composable
fun LeaderboardItem(name: String, place: Int) {

    val painter =
        rememberAsyncImagePainter(model = "https://images.unsplash.com/photo-1628373383885-4be0bc0172fa?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1301&q=80")

    Row (
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .height(80.dp)
            .padding(top = 4.dp, bottom = 6.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp))
            .padding(top = 5.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)

    ) {
        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "$place.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 15.sp,
                fontWeight = FontWeight.W300
            )
        }


        //Profile image, currently just image from drawable, need to get images from internet
        Card(
            shape = CircleShape,
            modifier = Modifier
                .size(48.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    shape = CircleShape
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Forest Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,

            )
        }

        Box(modifier = Modifier.weight(2f)) {
            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(start = 10.dp)
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            Text(
                text = "599",
                fontSize = 15.sp,
                fontWeight = FontWeight.W300,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LeaderboardPreview() {
    TestProjectTheme {
        LeaderboardScreen(null)
    }
}